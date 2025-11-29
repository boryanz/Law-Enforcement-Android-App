package com.boryanz.upszakoni.ui.screens.ai.information

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
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
        .padding(horizontal = 16.dp),
      verticalArrangement = Arrangement.SpaceBetween
    ) {
      Column(
        modifier = Modifier
          .fillMaxWidth()
          .weight(1f)
          .verticalScroll(rememberScrollState())
          .padding(vertical = 16.dp)
      ) {
        Text(
          modifier = Modifier.fillMaxWidth(),
          style = MaterialTheme.typography.headlineMedium,
          fontWeight = FontWeight.Bold,
          text = stringResource(R.string.prompt_information_what_is_heading)
        )
        Spacer.Vertical(8.dp)
        Text(
          modifier = Modifier.fillMaxWidth(),
          fontSize = 18.sp,
          text = stringResource(R.string.prompt_information_what_is_description)
        )
        Spacer.Vertical(16.dp)
        HorizontalDivider(modifier = Modifier.fillMaxWidth())
        Spacer.Vertical(16.dp)

        Text(
          modifier = Modifier.fillMaxWidth(),
          style = MaterialTheme.typography.headlineMedium,
          fontWeight = FontWeight.Bold,
          text = stringResource(R.string.prompt_information_how_to_use_heading)
        )
        Spacer.Vertical(8.dp)
        Text(
          modifier = Modifier.fillMaxWidth(),
          fontSize = 18.sp,
          text = stringResource(R.string.prompt_information_keywords_instruction)
        )
        Spacer.Vertical(16.dp)
        HorizontalDivider(modifier = Modifier.fillMaxWidth())
        Spacer.Vertical(16.dp)

        Text(
          modifier = Modifier.fillMaxWidth(),
          style = MaterialTheme.typography.headlineMedium,
          fontWeight = FontWeight.Bold,
          text = stringResource(R.string.prompt_information_important_heading)
        )
        Spacer.Vertical(8.dp)
        Text(
          modifier = Modifier.fillMaxWidth(),
          fontSize = 18.sp,
          text = stringResource(R.string.prompt_information_personal_data_warning)
        )
        Spacer.Vertical(16.dp)
        HorizontalDivider(modifier = Modifier.fillMaxWidth())
        Spacer.Vertical(16.dp)

        Text(
          modifier = Modifier.fillMaxWidth(),
          fontSize = 18.sp,
          text = stringResource(R.string.prompt_information_disclaimer)
        )
      }

      Button.Primary(
        modifier = Modifier
          .fillMaxWidth()
          .padding(vertical = 16.dp),
        title = stringResource(R.string.prompt_information_continue_button),
        onClick = onContinueClicked
      )
    }
  }
}

@Preview(showBackground = true)
@Composable
fun PromptInformationScreenPreview() {
  PromptInformationScreen(
    onContinueClicked = {},
    onBackClicked = {}
  )
}
