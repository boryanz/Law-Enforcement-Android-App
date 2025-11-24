package com.boryanz.upszakoni.ui.screens.ai

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.boryanz.upszakoni.R
import com.boryanz.upszakoni.ui.components.Button
import com.boryanz.upszakoni.ui.components.Icons
import com.boryanz.upszakoni.ui.components.Spacer
import com.boryanz.upszakoni.ui.components.UpsScaffold

@Composable
fun PromptInformationScreen(
  onContinueClicked: () -> Unit,
  onBackClicked: () -> Unit,
) {
  UpsScaffold(
    topBarTitle = { Text(text = stringResource(R.string.prompt_information_screen_title)) },
    navigationIcon = { Icons.Back(onClick = onBackClicked) }
  ) {
    Column(
      modifier = Modifier
        .fillMaxSize()
        .padding(it)
        .padding(all = 12.dp)
        .verticalScroll(rememberScrollState()),
      verticalArrangement = Arrangement.Top
    ) {
      Text(
        modifier = Modifier.fillMaxWidth(),
        style = MaterialTheme.typography.headlineMedium,
        text = stringResource(R.string.prompt_information_what_is_heading)
      )
      Spacer.Vertical(4.dp)
      Text(
        modifier = Modifier.fillMaxWidth(),
        style = MaterialTheme.typography.bodyLarge,
        text = stringResource(R.string.prompt_information_what_is_description)
      )
      Spacer.Vertical(12.dp)
      Text(
        modifier = Modifier.fillMaxWidth(),
        style = MaterialTheme.typography.headlineMedium,
        text = stringResource(R.string.prompt_information_how_to_use_heading)
      )
      Spacer.Vertical(4.dp)
      Text(
        modifier = Modifier.fillMaxWidth(),
        fontSize = 18.sp,
        fontWeight = FontWeight.Bold,
        text = stringResource(R.string.prompt_information_keywords_instruction)
      )
      Spacer.Vertical(12.dp)
      Text(
        modifier = Modifier.fillMaxWidth(),
        style = MaterialTheme.typography.headlineMedium,
        text = stringResource(R.string.prompt_information_important_heading)
      )
      Spacer.Vertical(4.dp)
      Text(
        modifier = Modifier.fillMaxWidth(),
        style = MaterialTheme.typography.bodyLarge,
        text = stringResource(R.string.prompt_information_personal_data_warning)
      )
      Spacer.Vertical(18.dp)
      Text(
        modifier = Modifier.fillMaxWidth(),
        style = MaterialTheme.typography.bodyLarge,
        text = stringResource(R.string.prompt_information_disclaimer)
      )
      Spacer(modifier = Modifier.weight(1f))
      Button.Primary(
        title = stringResource(R.string.prompt_information_continue_button),
        onClick = onContinueClicked
      )
    }
  }
}
