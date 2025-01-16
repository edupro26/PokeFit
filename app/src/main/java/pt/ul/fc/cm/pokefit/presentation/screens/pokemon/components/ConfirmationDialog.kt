package pt.ul.fc.cm.pokefit.presentation.screens.pokemon.components

import androidx.compose.material3.AlertDialog
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.unit.dp
import pt.ul.fc.cm.pokefit.R
import pt.ul.fc.cm.pokefit.domain.model.pokemon.Pokemon

@Composable
fun ConfirmationDialog(
    pokemon: Pokemon,
    onConfirm: (Pokemon) -> Unit,
    onCancel: () -> Unit
) {
    AlertDialog(
        text = {
            val name = pokemon.name!!.replaceFirstChar { it.uppercase() }
            Text(
                text = buildAnnotatedString {
                    append("Select")
                    append(" $name ")
                    append("as your starter pokemon?")
                },
                style = MaterialTheme.typography.bodyLarge
            )
        },
        confirmButton = {
            TextButton(
                onClick = { onConfirm(pokemon) }
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