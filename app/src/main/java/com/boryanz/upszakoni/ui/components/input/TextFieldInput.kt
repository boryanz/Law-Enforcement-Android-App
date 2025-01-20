package com.boryanz.upszakoni.ui.components.input

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.boryanz.upszakoni.ui.theme.KataSampleAppTheme

object TextFieldInput {

    @Composable
    fun BaseOutline(
        modifier: Modifier = Modifier,
        labelText: String,
        value: String,
        isError: Boolean = false,
        hint: String = "",
        keyboardOptions: KeyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
        onValueChanged: (String) -> Unit,
    ) {
        OutlinedTextField(
            modifier = modifier
                .fillMaxWidth(),
            value = value,
            isError = isError,
            keyboardOptions = keyboardOptions,
            label = { Text(labelText) },
            placeholder = { Text(hint) },
            onValueChange = onValueChanged
        )
    }
}


@Preview(showBackground = true)
@Composable
private fun TextFieldInputPreview() {
    KataSampleAppTheme {
        TextFieldInput.BaseOutline(
            modifier = Modifier.padding(6.dp),
            labelText = "Label test",
            value = "",
            onValueChanged = {})
    }
}