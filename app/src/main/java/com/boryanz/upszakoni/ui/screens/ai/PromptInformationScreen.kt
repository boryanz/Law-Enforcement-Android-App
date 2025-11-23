package com.boryanz.upszakoni.ui.screens.ai

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.boryanz.upszakoni.ui.components.Button
import com.boryanz.upszakoni.ui.components.Icons
import com.boryanz.upszakoni.ui.components.UpsScaffold

@Composable
fun PromptInformationScreen(
  onContinueClicked: () -> Unit,
  onBackClicked: () -> Unit,
) {
  UpsScaffold(
    topBarTitle = { Text(text = "Информации за генерирање") },
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
        text = "Генерирањето на белешки користи вештачка интелигенција и е предмет на вашата одговорност.\n\n" +
            "ЗАБРАНА: Не смеете да внесувате никакви вид на лични податоци во промптот. Секаков вид на личен податок ќе бидеш забранен и белешката нема да биде генерирана.\n\n" +
            "Оваа функција е наменета за образовна и инспиративна намена и не смее да се прима како гарантирана. Содржината генерирана од овој систем е само суgestija и не треба да се користи како крајна верзија без внимателна преглед и поправка.\n\n" +
            "ОГРАНИЧУВАЊЕ: Дневно имате право на максимално 5 генерирани белешки."
      )
      Spacer(modifier = Modifier.weight(1f))
      Button.Primary(
        title = "Продолжи",
        onClick = onContinueClicked
      )
    }
  }
}
