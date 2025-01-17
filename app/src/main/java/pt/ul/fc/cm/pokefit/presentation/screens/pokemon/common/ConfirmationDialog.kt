package pt.ul.fc.cm.pokefit.presentation.screens.pokemon.common

import androidx.compose.material3.AlertDialog
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import pt.ul.fc.cm.pokefit.R

@Composable
fun ConfirmationDialog(
    text: String,
    onCancel: () -> Unit,
    onConfirm: () -> Unit
) {
    AlertDialog(
        text = {
            Text(
                text = text,
                style = MaterialTheme.typography.bodyLarge
            )
        },
        confirmButton = {
            TextButton(
                onClick = { onConfirm() }
            ) {
                Text(stringResource(R.string.confirm))
            }
        },
        dismissButton = {
            TextButton(
                onClick = { onCancel() }
            ) {
                Text(stringResource(R.string.cancel))
            }
        },
        onDismissRequest = { onCancel() },
        containerColor = MaterialTheme.colorScheme.primaryContainer,
        tonalElevation = 8.dp
    )
}