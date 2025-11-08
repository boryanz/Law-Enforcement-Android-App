package com.boryanz.upszakoni.ui.screens.bonussalary

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.boryanz.upszakoni.data.local.sharedprefs.SharedPrefsManager
import com.boryanz.upszakoni.domain.remoteconfig.RemoteConfigRepository
import com.boryanz.upszakoni.ui.navigation.navgraph.BonusSalaryNavigationGraph
import com.boryanz.upszakoni.ui.navigation.navgraph.overtimetracking.OverTimeTrackNavigationGraph
import com.boryanz.upszakoni.ui.theme.UpsTheme
import org.koin.android.ext.android.inject

/**
 * Handling the bonus salary statistics like overtime hours, paid leave and sick days
 */
class BonusSalaryActivity : ComponentActivity() {

  private val localStorage: SharedPrefsManager by inject()
  private val remoteConfigRepository: RemoteConfigRepository by inject()
  private val shouldBackportOvertimeFeature =
    remoteConfigRepository.remoteConfigState.value.shouldBackportOvertimeTracking

  companion object {
    fun createIntent(context: Context) = Intent(context, BonusSalaryActivity::class.java)
  }

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    enableEdgeToEdge()
    setContent {
      UpsTheme {
        with(localStorage) {
          when {
            shouldBackportOvertimeFeature -> {
              BonusSalaryNavigationGraph(
                shouldBackportOvertimeTracking = true,
                onMigrationAccepted = { restartActivity() },
                onBackNavigated = { finish() })
            }

            hasUserMigratedToNewOvertimeTracking() -> {
              OverTimeTrackNavigationGraph(
                onBackNavigated = { finish() }
              )
            }

            hasUserRejectedOvertimeTrackingMigration() -> {
              BonusSalaryNavigationGraph(
                onMigrationAccepted = { restartActivity() },
                onBackNavigated = { finish() })
            }

            else -> {
              BonusSalaryNavigationGraph(
                onMigrationAccepted = { restartActivity() },
                onBackNavigated = { finish() })
            }
          }
        }
      }
    }
  }

  private fun restartActivity() {
    finish()
    startActivity(createIntent(this@BonusSalaryActivity))
  }
}