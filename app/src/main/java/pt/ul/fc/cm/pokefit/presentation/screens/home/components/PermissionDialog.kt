package pt.ul.fc.cm.pokefit.presentation.screens.home.components

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.provider.Settings
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp

@Composable
fun PermissionDialog(
    title: String,
    contentText: String,
    buttonText: String,
    onConfirm: () -> Unit,
    onDismiss: () -> Unit = {}
) {
    val context = LocalContext.current
    AlertDialog(
        onDismissRequest = { onDismiss() },
        title = { Text(title) },
        text = { Text(contentText) },
        confirmButton = {
            TextButton(onClick = {
                onConfirm()
                openSettings(context)
            }) {
                Text(buttonText)
            }
        },
        containerColor = MaterialTheme.colorScheme.primaryContainer,
        tonalElevation = 8.dp
    )
}

private fun openSettings(context: Context) {
    val intent = Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS).apply {
        data = Uri.fromParts("package", context.packageName, null)
    }
    context.startActivity(intent)
}