package com.boryanz.upszakoni.ui.screens.bonussalary

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import com.boryanz.upszakoni.ui.navigation.navgraph.BonusSalaryNavigationGraph
import com.boryanz.upszakoni.ui.theme.KataSampleAppTheme

/**
 * Handling the bonus salary statistics like overtime hours, paid leave and sick days
 */
class BonusSalaryActivity : AppCompatActivity() {

    companion object {
        fun createIntent(context: Context) = Intent(context, BonusSalaryActivity::class.java)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            KataSampleAppTheme {
                BonusSalaryNavigationGraph()
            }
        }
    }
}