package com.boryanz.upszakoni.ui.screens.error

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.SignalWifiConnectedNoInternet4
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.boryanz.upszakoni.ui.components.Spacer
import com.boryanz.upszakoni.ui.components.UpsScaffold
import com.boryanz.upszakoni.ui.theme.UpsTheme

class NoConnectionActivity : ComponentActivity() {

  companion object Companion {
    fun createIntent(context: Context) = Intent(context, NoConnectionActivity::class.java)
  }

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)

    setContent {
      UpsTheme {
        NoConnectionContent { finish() }
      }
    }
  }
}

@Composable
fun NoConnectionContent(onClick: () -> Unit) {
  UpsScaffold(
    topBarTitle = {},
    navigationIcon = {
      com.boryanz.upszakoni.ui.components.Icons.Back(onClick = onClick)
    }
  ) {
    Column(
      modifier = Modifier
        .fillMaxSize()
        .padding(12.dp),
      verticalArrangement = Arrangement.Center,
      horizontalAlignment = Alignment.CenterHorizontally
    ) {
      Icon(
        modifier = Modifier.size(32.dp),
        imageVector = Icons.Default.SignalWifiConnectedNoInternet4,
        contentDescription = ""
      )
      Spacer.Vertical(8.dp)
      Text(text = "Немате интернет конекција", style = MaterialTheme.typography.titleMedium)
    }
  }
}

@Preview
@Composable
private fun NoConnectionContentPreview() {
  NoConnectionContent {}
}