package com.boryanz.upszakoni

import android.content.ActivityNotFoundException
import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.core.net.toUri
import com.boryanz.upszakoni.customtab.CustomTabLauncher
import com.boryanz.upszakoni.data.local.sharedprefs.SharedPrefsManager
import com.boryanz.upszakoni.domain.remoteconfig.FirebaseRemoteConfig
import com.boryanz.upszakoni.ui.components.AppCallbacks
import com.boryanz.upszakoni.ui.components.BottomNavItem
import com.boryanz.upszakoni.ui.components.BottomNavWrapper
import com.boryanz.upszakoni.ui.components.LocalAppCallbacks
import com.boryanz.upszakoni.ui.navigation.navgraph.BonusSalaryNavigationGraph
import com.boryanz.upszakoni.ui.navigation.navgraph.OwnedItemsNavigationGraph
import com.boryanz.upszakoni.ui.navigation.navgraph.main.NavigationGraph
import com.boryanz.upszakoni.ui.navigation.navgraph.overtimetracking.OverTimeTrackNavigationGraph
import com.boryanz.upszakoni.ui.theme.UpsTheme
import org.koin.compose.koinInject

private const val GOOGLE_PLAYSTORE_APP_LINK =
  "https://play.google.com/store/apps/details?id=com.boryanz.upszakoni&hl=mk"

class MainActivity : ComponentActivity() {

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    enableEdgeToEdge()

    setContent {
      UpsTheme {
        CompositionLocalProvider(
          LocalAppCallbacks provides AppCallbacks(
            onShareAppClicked = ::shareApp,
            onAppUpdateClicked = ::openAppInGooglePlay,
            onFeedbackFormClicked = {
              CustomTabLauncher().launch(
                context = this@MainActivity,
                url = getString(R.string.feedback_form_url)
              )
            }
          )
        ) {
          MainScreen()
        }
      }
    }
  }

  @Composable
  private fun MainScreen() {
    var selectedTab by remember { mutableStateOf(BottomNavItem.Laws) }

    BottomNavWrapper(
      selected = selectedTab,
      onLawsClicked = { selectedTab = BottomNavItem.Laws },
      onOvertimeClicked = { selectedTab = BottomNavItem.Overtime },
      onEquipmentClicked = { selectedTab = BottomNavItem.Equipment },
    ) {
      when (selectedTab) {
        BottomNavItem.Laws -> NavigationGraph()
        BottomNavItem.Overtime -> OvertimeSection(
          onBackNavigated = { selectedTab = BottomNavItem.Laws },
        )

        BottomNavItem.Equipment -> OwnedItemsNavigationGraph(
          onBackNavigated = { selectedTab = BottomNavItem.Laws },
        )
      }
    }
  }

  @Composable
  private fun OvertimeSection(onBackNavigated: () -> Unit) {
    val localStorage = koinInject<SharedPrefsManager>()
    val remoteConfig = koinInject<FirebaseRemoteConfig>()
    val shouldBackportOvertimeTracking =
      remember { remoteConfig.remoteConfigState.value.shouldBackportOvertimeTracking }
    var migrationKey by remember { mutableIntStateOf(0) }

    androidx.compose.runtime.key(migrationKey) {
      with(localStorage) {
        when {
          shouldBackportOvertimeTracking -> BonusSalaryNavigationGraph(
            shouldBackportOvertimeTracking = true,
            onMigrationAccepted = { migrationKey++ },
            onBackNavigated = onBackNavigated,
          )

          hasUserMigratedToNewOvertimeTracking() -> OverTimeTrackNavigationGraph(
            onBackNavigated = onBackNavigated,
          )

          hasUserRejectedOvertimeTrackingMigration() -> BonusSalaryNavigationGraph(
            onMigrationAccepted = { migrationKey++ },
            onBackNavigated = onBackNavigated,
          )

          else -> BonusSalaryNavigationGraph(
            onMigrationAccepted = { migrationKey++ },
            onBackNavigated = onBackNavigated,
          )
        }
      }
    }
  }

  private fun shareApp() {
    val intent = Intent()
    intent.setAction(Intent.ACTION_SEND)
    intent.setType("text/plain")
    intent.putExtra(
      Intent.EXTRA_TEXT,
      GOOGLE_PLAYSTORE_APP_LINK
    )
    startActivity(Intent.createChooser(intent, "УПС мобилна апликација"))
  }


  fun openAppInGooglePlay() {
    try {
      startActivity(
        Intent(
          Intent.ACTION_VIEW,
          "market://details?id=$packageName".toUri()
        )
      )
    } catch (anfe: ActivityNotFoundException) {
      startActivity(
        Intent(
          Intent.ACTION_VIEW,
          "https://play.google.com/store/apps/details?id=$packageName".toUri()
        )
      )
    }
  }
}