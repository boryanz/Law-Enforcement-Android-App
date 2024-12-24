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
import com.boryanz.upszakoni.ui.components.Spacer
import com.boryanz.upszakoni.ui.components.UpsScaffold

@Composable
fun PrivacyPolicyScreen(
    continueButton: (@Composable () -> Unit)? = null
) {
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
            continueButton?.let {
                Spacer.Vertical(12.dp)
                it()
            }
        }
    }
}


private val privacyPolictyText =
    "„УПС“ апликацијата не го претставува Министерството за внатрешни работи, ниту било каква државна институција. Оваа апликација не собира, ниту обработува лични податоци на корисниците, како што се име, адреса или телефонски број. Апликацијата не пристапува, ниту користи физичка локација на корисниците, а сите податоци што се користат се строго статички, јавно достапни информации кои можат да се најдат од секого. \n" +
            "\n" +
            "Ве молиме, имајте во предвид некои од правните членови што се користат во оваа апликација можат да станат застарени во одреден момент и ваша должност е да ја проверите точноста на информациите. Заедницата ќе има корист доколку корисниците достават нови правни, пречистени текстови или предлози за подобрување на следниот меил: boryans.co@gmail.com\n" +
            "\n" +
            "Апликацијата е развиена доброволно од поединец, со цел да им помогне на лицата кои се спроведувачи на законот."