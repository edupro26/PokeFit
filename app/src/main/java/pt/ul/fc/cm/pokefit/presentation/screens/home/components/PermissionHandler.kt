package pt.ul.fc.cm.pokefit.presentation.screens.home.components

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.res.stringResource
import androidx.core.content.ContextCompat
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import androidx.lifecycle.compose.LocalLifecycleOwner
import pt.ul.fc.cm.pokefit.R

@Composable
fun PermissionHandler(
    context: Context,
    countSteps: () -> Unit = {}
) {
    var isGranted by remember { mutableStateOf(checkPermission(context)) }
    var showRationale by remember { mutableStateOf(false) }
    val launcher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.RequestPermission(),
        onResult = { granted: Boolean ->
            if (granted) {
                isGranted = true
                countSteps()
            } else {
                showRationale = true
            }
        }
    )
    isGranted = rememberPermissionState(isGranted, context, countSteps)
    when {
        !isGranted -> {
            LaunchedEffect(Unit) {
                launcher.launch(Manifest.permission.ACTIVITY_RECOGNITION)
            }
        }
        else -> { countSteps() }
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

@Composable
private fun rememberPermissionState(
    isGranted: Boolean,
    context: Context,
    countSteps: () -> Unit
): Boolean {
    var result = isGranted
    val lifecycleOwner = LocalLifecycleOwner.current
    DisposableEffect(lifecycleOwner) {
        val observer = LifecycleEventObserver { _, event ->
            if (event == Lifecycle.Event.ON_RESUME) {
                result = checkPermission(context)
                if (result) {
                    countSteps()
                }
            }
        }
        lifecycleOwner.lifecycle.addObserver(observer)
        onDispose { lifecycleOwner.lifecycle.removeObserver(observer) }
    }
    return result
}

private fun checkPermission(context: Context): Boolean {
    return ContextCompat.checkSelfPermission(
        context,
        Manifest.permission.ACTIVITY_RECOGNITION
    ) == PackageManager.PERMISSION_GRANTED
}