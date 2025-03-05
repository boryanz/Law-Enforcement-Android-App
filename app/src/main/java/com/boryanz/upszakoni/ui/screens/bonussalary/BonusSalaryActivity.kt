package com.boryanz.upszakoni.ui.screens.bonussalary

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.boryanz.upszakoni.domain.bonussalary.BonusSalaryRepository
import com.boryanz.upszakoni.domain.remoteconfig.RemoteConfigRepository
import com.boryanz.upszakoni.ui.navigation.navgraph.overtimetracking.OverTimeTrackNavigationGraph
import org.koin.android.ext.android.inject

/**
 * Handling the bonus salary statistics like overtime hours, paid leave and sick days
 */
class BonusSalaryActivity : ComponentActivity() {

    private val remoteConfigRepository: RemoteConfigRepository by inject()
    private val shouldBackportOvertimeFeature =
        remoteConfigRepository.remoteConfigState.value.shouldBackportOvertimeTracking

    private val bonusSalaryRepository: BonusSalaryRepository by inject()

    companion object {
        fun createIntent(context: Context) = Intent(context, BonusSalaryActivity::class.java)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            OverTimeTrackNavigationGraph(
                onBackNavigated = ::finish,
                onMigrationAccepted = ::restartActivity
            )


//            UpsTheme {
//                with(SharedPrefsDao) {
//                    when {
//                        shouldBackportOvertimeFeature -> {
//                            BonusSalaryNavigationGraph(
//                                shouldBackportOvertimeTracking = true,
//                                onMigrationAccepted = {
//                                    runCatching {
//                                        /*hard reset on the db*/
//                                        cacheDir.deleteRecursively()
//                                        dataDir.deleteRecursively()
//                                    }
//                                    lifecycleScope.launch {
//                                        bonusSalaryRepository.generateDefaultDataAfterMigration()
//                                    }.also {
//                                        restartActivity()
//                                    }
//                                },
//                                onBackNavigated = { finish() })
//                        }
//
//                        hasUserMigratedToNewOvertimeTracking() -> {
//                            OverTimeTrackNavigationGraph(
//                                onBackNavigated = { finish() }
//                            )
//                        }
//
//                        hasUserRejectedOvertimeTrackingMigration() -> {
//                            BonusSalaryNavigationGraph(
//                                onMigrationAccepted = { restartActivity() },
//                                onBackNavigated = { finish() })
//                        }
//
//                        else -> {
//                            BonusSalaryNavigationGraph(
//                                onMigrationAccepted = { restartActivity() },
//                                onBackNavigated = { finish() })
//                        }
//                    }
//                }
        }
    }

    private fun restartActivity() {
        finish()
        startActivity(createIntent(this@BonusSalaryActivity))
    }
}