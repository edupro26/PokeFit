package pt.ul.fc.cm.pokefit.ui.screens.auth.components

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import pt.ul.fc.cm.pokefit.R

@Composable
fun GeneralTextField(labelValue: String) {
    var textValue by remember { mutableStateOf("") }
    OutlinedTextField(
        modifier = Modifier
            .fillMaxWidth(),
        label = { Text(text = labelValue) },
        singleLine = true,
        maxLines = 1,
        value = textValue,
        onValueChange = { textValue = it },
        keyboardActions = KeyboardActions(
            onDone = { /*TODO*/ }
        )
    )
}

@Composable
fun PasswordTextField(labelValue: String) {
    var textValue by remember { mutableStateOf("") }
    var show by remember { mutableStateOf(false) }
    OutlinedTextField(
        modifier = Modifier
            .fillMaxWidth(),
        label = { Text(text = labelValue) },
        singleLine = true,
        maxLines = 1,
        value = textValue,
        onValueChange = { textValue = it },
        keyboardActions = KeyboardActions(
            onDone = { /*TODO*/ }
        ),
        visualTransformation = if (show) VisualTransformation.None else PasswordVisualTransformation(),
        trailingIcon = {
            IconButton(
                onClick = { show = !show }
            ) {
                Icon(
                    painter = painterResource(
                        id = if (show) R.drawable.ic_visibility else R.drawable.ic_visibility_off
                    ),
                    contentDescription = if (show) "Hide password" else "Show password",
                    tint = Color.Unspecified
                )
            }
        }
    )
}