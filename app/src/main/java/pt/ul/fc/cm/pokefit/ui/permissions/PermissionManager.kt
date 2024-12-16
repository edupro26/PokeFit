package pt.ul.fc.cm.pokefit.ui.permissions

import android.Manifest
import android.app.Activity
import android.app.AlertDialog
import android.content.Context

class PermissionManager(private val activity: Activity) {

    private val permissionHelper = PermissionHelper(activity)
    private val REQUEST_CODE_ACTIVITY_RECOGNITION = 100

    fun checkAndRequestPermission(
        onPermissionGranted: () -> Unit,
        onPermissionDenied: () -> Unit
    ) {
        if (permissionHelper.hasPermission(Manifest.permission.ACTIVITY_RECOGNITION)) {
            onPermissionGranted()
        } else {
            if (permissionHelper.shouldShowRationale(Manifest.permission.ACTIVITY_RECOGNITION)) {
                showRationaleDialog(onPermissionGranted)
            } else {
                permissionHelper.requestPermission(Manifest.permission.ACTIVITY_RECOGNITION, REQUEST_CODE_ACTIVITY_RECOGNITION)
            }
        }
    }

    private fun showRationaleDialog(onPermissionGranted: () -> Unit) {
        AlertDialog.Builder(activity)
            .setTitle("Permissão Necessária")
            .setMessage("Precisamos da permissão de reconhecimento de atividade para monitorizar os seus passos.")
            .setPositiveButton("Conceder Permissão") { _, _ ->
                permissionHelper.requestPermission(Manifest.permission.ACTIVITY_RECOGNITION, REQUEST_CODE_ACTIVITY_RECOGNITION)
            }
            .setNegativeButton("Cancelar") { dialog, _ ->
                dialog.dismiss()
            }
            .show()
    }

    fun handlePermissionResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray,
        onPermissionGranted: () -> Unit,
        onPermissionDenied: () -> Unit
    ) {
        permissionHelper.handlePermissionResult(
            requestCode,
            permissions,
            grantResults,
            onGranted = onPermissionGranted,
            onDenied = onPermissionDenied
        )
    }

    fun hasPermission(activityRecognition: String): Boolean {
        return permissionHelper.hasPermission(activityRecognition)
    }
}

