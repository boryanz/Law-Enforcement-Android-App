package com.boryanz.upszakoni.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.boryanz.upszakoni.ui.components.UpsScaffold

@Composable
fun PrivacyPolicyScreen() {
    UpsScaffold(
        topBarTitle = { Text(text = "Политика за приватност") }
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(it)
                .padding(all = 24.dp)
                .verticalScroll(rememberScrollState()),
            verticalArrangement = Arrangement.Top
        ) {
            Text(text = privacyPolictyText)
        }
    }
}


private val privacyPolictyText =
    "Оваа апликација не собира ниту обработува лични податоци на корисниците, како што се име, адреса или телефонски број. Апликацијата не пристапува ниту користи физичка локација на корисниците, а сите податоци што се користат се строго статички, јавно достапни информации кои можат да се најдат од секој. Апликацијата не претставува никаква владина институција или официјални органи.\n" +
            "\n" +
            "Ве молиме имајте на ум дека некои од правните членови што се користат во оваа апликација можат да станат застарени во одреден момент. Користете ја апликацијата со внимателност и проверете ја точноста на информациите. Заедницата ќе има корист ако корисниците достават нови правни членови или повратни информации за подобрување на апликацијата на boryans.co@gmail.com\n" +
            "\n" +
            "Оваа апликација е развиена доброволно, со цел да им помогне на лица кој се сппроведувачи на законот."