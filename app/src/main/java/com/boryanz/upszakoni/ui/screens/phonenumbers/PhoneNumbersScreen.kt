package com.boryanz.upszakoni.ui.screens.phonenumbers

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.boryanz.upszakoni.R
import com.boryanz.upszakoni.data.phoneNumbers
import com.boryanz.upszakoni.ui.components.Icons
import com.boryanz.upszakoni.ui.components.ItemWithDescription
import com.boryanz.upszakoni.ui.components.UpsScaffold

@Composable
fun PhoneNumbersScreen(
    onContactClicked: (phoneNumber: String) -> Unit,
    onBackClicked: () -> Unit,
) {
    UpsScaffold(
        topBarTitle = { Text(text = stringResource(R.string.phone_numbers_title), fontWeight = FontWeight.Bold) },
        navigationIcon = {
            Icons.Back(onClick = onBackClicked)
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(8.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Top
        ) {
            LazyColumn {
                items(phoneNumbers) { item ->
                    ItemWithDescription(
                        isEnabled = true,
                        title = item.policeStation,
                        description = item.contactPhone,
                        onClick = { onContactClicked(item.contactPhone) }
                    )
                    Spacer(modifier = Modifier.padding(vertical = 4.dp))
                }
            }
        }
    }
}
