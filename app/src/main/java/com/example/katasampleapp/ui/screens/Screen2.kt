package com.example.katasampleapp.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.katasampleapp.ui.theme.KataSampleAppTheme

@Composable
fun Screen2(
    onNavigateBack: () -> Unit
) {
    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text("Screen 2")
        Spacer(modifier = Modifier.padding(vertical = 12.dp))
        Button(onClick = onNavigateBack) {
            Text("Go back")
        }
    }
}


@Preview(showBackground = true)
@Composable
private fun Screen2Preview() {
    KataSampleAppTheme {
        Screen2 {  }
    }
}