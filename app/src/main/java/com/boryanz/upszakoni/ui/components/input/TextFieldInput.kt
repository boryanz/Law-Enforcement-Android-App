package com.boryanz.upszakoni.ui.components.input

import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.LocalTextStyle
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldColors
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.boryanz.upszakoni.ui.theme.UpsTheme

object TextFieldInput {

    @Composable
    fun BaseOutline(
        modifier: Modifier = Modifier,
        labelText: String,
        value: String,
        textFieldColors: TextFieldColors = OutlinedTextFieldDefaults.colors(),
        textStyle: TextStyle = LocalTextStyle.current,
        labelTextStyle: TextStyle = MaterialTheme.typography.bodySmall,
        trailingIcon: (@Composable () -> Unit)? = null,
        leadingIcon: (@Composable () -> Unit)? = null,
        isError: Boolean = false,
        hint: String = "",
        isReadOnly: Boolean = false,
        interactionSource: MutableInteractionSource? = null,
        keyboardOptions: KeyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
        onValueChanged: (String) -> Unit,
    ) {

        OutlinedTextField(
            modifier = modifier
                .fillMaxWidth(),
            value = value,
            isError = isError,
            keyboardOptions = keyboardOptions,
            interactionSource = interactionSource,
            label = { Text(text = labelText, style = labelTextStyle) },
            placeholder = { Text(hint) },
            onValueChange = onValueChanged,
            trailingIcon = trailingIcon,
            leadingIcon = leadingIcon,
            colors = textFieldColors,
            textStyle = textStyle,
            readOnly = isReadOnly
        )
    }
}


@Preview(showBackground = true)
@Composable
private fun TextFieldInputPreview() {
    UpsTheme {
        TextFieldInput.BaseOutline(
            modifier = Modifier.padding(6.dp),
            labelText = "Label test",
            value = "",
            interactionSource = remember { MutableInteractionSource() },
            onValueChanged = {})
    }
}