package pt.ul.fc.cm.pokefit.presentation.screens.home.home.components

import android.Manifest
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.res.stringResource
import androidx.core.content.ContextCompat
import pt.ul.fc.cm.pokefit.R
import pt.ul.fc.cm.pokefit.domain.service.TrackingService

@Composable
fun PermissionHandler(context: Context) {
    val activity = Manifest.permission.ACTIVITY_RECOGNITION
    val notification = Manifest.permission.POST_NOTIFICATIONS
    val permissions = arrayOf(notification, activity)
    var showRationale by remember { mutableStateOf(false) }
    var hasActivityPermission by remember {
        mutableStateOf(checkPermission(context, activity))
    }
    val permissionLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.RequestMultiplePermissions(),
        onResult = { perms ->
            perms.keys.forEach { permission ->
                if (permission == activity) {
                    if (perms[permission] == true) {
                        startHealthService(context)
                    } else {
                        showRationale = true
                    }
                }
            }
        }
    )

    when {
        !hasActivityPermission -> {
            LaunchedEffect(Unit) {
                val request = permissions
                    .filter { !checkPermission(context, it) }
                    .toTypedArray()
                if (request.isNotEmpty()) {
                    permissionLauncher.launch(request)
                }
            }
        }
        else -> { startHealthService(context) }
    }

    if (showRationale) {
        PermissionDialog(
            title = stringResource(R.string.permission_required),
            contentText = stringResource(R.string.activity_permission),
            buttonText = stringResource(R.string.go_to_settings),
            onConfirm = { showRationale = false },
            onDismiss = { showRationale = false }
        )
    }
}

private fun startHealthService(context: Context) {
    Intent(context, TrackingService::class.java).also {
        it.action = TrackingService.Action.START.toString()
        context.startService(it)
    }
}

private fun checkPermission(context: Context, permission: String): Boolean {
    return ContextCompat.checkSelfPermission(
        context, permission
    ) == PackageManager.PERMISSION_GRANTED
}