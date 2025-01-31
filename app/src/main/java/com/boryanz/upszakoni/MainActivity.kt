package com.boryanz.upszakoni

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.boryanz.upszakoni.ui.navigation.navgraph.NavigationGraph
import com.boryanz.upszakoni.ui.theme.KataSampleAppTheme

private const val GOOGLE_PLAYSTORE_APP_LINK = "https://play.google.com/store/apps/details?id=com.boryanz.upszakoni&hl=mk"

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        setContent {
            KataSampleAppTheme {
                NavigationGraph(onShareAppClicked = ::shareApp)
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
}