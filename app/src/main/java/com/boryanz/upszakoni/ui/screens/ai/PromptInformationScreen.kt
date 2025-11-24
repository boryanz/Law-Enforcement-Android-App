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
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
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
    topBarTitle = { Text(text = "Начин на користење") },
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
        text = "Што е ова?"
      )
      Spacer.Vertical(4.dp)
      Text(
        modifier = Modifier.fillMaxWidth(),
        style = MaterialTheme.typography.bodyLarge,
        text = "Генератор на белешки сo помош на вештачка интелигенција."
      )
      Spacer.Vertical(12.dp)
      Text(
        modifier = Modifier.fillMaxWidth(),
        style = MaterialTheme.typography.headlineMedium,
        text = "Важно!"
      )
      Spacer.Vertical(4.dp)
      Text(
        modifier = Modifier.fillMaxWidth(),
        style = MaterialTheme.typography.bodyLarge,
        text = "ПО СЕКОЈА ЦЕНА ИЗБЕГНУВАЈТЕ ДА ВНЕСУВАТЕ ЛИЧНИ ПОДАТОЦИ ОД СТРАНКИ!\nСекој обид на внес на матичен број, дата на раѓање или телефонски број ќе бидат одбиени и белешката нема да биде креирана."
      )
      Spacer.Vertical(12.dp)
      Text(
        modifier = Modifier.fillMaxWidth(),
        style = MaterialTheme.typography.headlineMedium,
        text = "Како правилно да го користам генераторот?"
      )
      Spacer.Vertical(4.dp)
      Text(
        modifier = Modifier.fillMaxWidth(),
        fontSize = 18.sp,
        fontWeight = FontWeight.Bold,
        text = "Внесете само КЛУЧНИ ЗБОРОВИ.\n\nПример:\nТепачка, Улица _________, Две машки лица, Физичка пресметка, Легитимирање, ППН, Известена Дежурна служба."
      )
      Spacer.Vertical(18.dp)
      Text(
        modifier = Modifier.fillMaxWidth(),
        style = MaterialTheme.typography.bodyLarge,
        text = "Оваа функција е наменета за образовна и инспиративна намена при изговување на вашиот материјал. Содржината генерирана од овој систем е само сугестија и не треба да се користи како крајна верзија!"
      )
      Spacer(modifier = Modifier.weight(1f))
      Button.Primary(
        title = "Продолжи",
        onClick = onContinueClicked
      )
    }
  }
}
