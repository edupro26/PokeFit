package pt.ul.fc.cm.pokefit.domain.service

import android.Manifest
import android.app.Notification
import android.app.PendingIntent
import android.app.Service
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.IBinder
import android.provider.Settings
import android.util.Log
import androidx.core.app.ActivityCompat
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.core.content.ContextCompat
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import pt.ul.fc.cm.pokefit.R
import pt.ul.fc.cm.pokefit.domain.repository.AuthRepository
import pt.ul.fc.cm.pokefit.domain.repository.StepCountRepository
import pt.ul.fc.cm.pokefit.domain.sensor.MeasurableSensor
import javax.inject.Inject

@AndroidEntryPoint
class TrackingService : Service() {

    @Inject lateinit var stepCounter: MeasurableSensor
    @Inject lateinit var authRepository: AuthRepository
    @Inject lateinit var stepsRepository: StepCountRepository

    private val serviceScope = CoroutineScope(Dispatchers.Default)
    private var lastSavedValue: Long = 0

    enum class Action { START, STOP }

    override fun onBind(intent: Intent?): IBinder? = null

    override fun onCreate() {
        super.onCreate()
        if (!hasRequiredPermission()) {
            showPermissionRequiredNotification()
            stopSelf()
            return
        }
        startForegroundService()
    }

    override fun onDestroy() {
        super.onDestroy()
        stepCounter.stopListening()
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        when (intent?.action) {
            Action.START.toString() -> start()
            Action.STOP.toString() -> stop()
        }
        return super.onStartCommand(intent, flags, startId)
    }

    private fun start() {
        if (!stepCounter.isListening()) {
            stepCounter.startListening()
            saveSensorValues()
            Log.d(TAG, "Started tracking service")
        }
    }

    private fun stop() {
        Log.d(TAG, "Stopped tracking service")
        stopSelf()
    }

    private fun startForegroundService() {
        val notification = buildForegroundNotification()
        startForeground(FOREGROUND_ID, notification)
    }

    private fun saveSensorValues() {
        stepCounter.setOnSensorValuesChangedListener { values ->
            val steps = values.firstOrNull()?.toLong() ?: 0
            serviceScope.launch {
                if (steps > lastSavedValue) {
                    val uid = authRepository.currentUser?.uid
                    if (uid != null) {
                        stepsRepository.saveSteps(steps, uid)
                        lastSavedValue = steps
                    }
                }
            }
        }
    }

    private fun hasRequiredPermission(): Boolean {
        val permission = Manifest.permission.ACTIVITY_RECOGNITION
        return ContextCompat.checkSelfPermission(
            this@TrackingService, permission
        ) == PackageManager.PERMISSION_GRANTED
    }

    private fun showPermissionRequiredNotification() {
        val notification = buildPermissionNotification()
        val notificationManager = NotificationManagerCompat.from(this)
        if (ActivityCompat.checkSelfPermission(
                this,
                Manifest.permission.POST_NOTIFICATIONS
            ) == PackageManager.PERMISSION_GRANTED
        ) {
            notificationManager.notify(PERMISSION_ID, notification)
        }
    }

    private fun buildForegroundNotification(): Notification{
        return NotificationCompat.Builder(this, HEALTH_CHANNEL)
            .setSmallIcon(R.drawable.ic_stats_steps)
            .setContentText(FOREGROUND_TEXT)
            .setStyle(NotificationCompat.BigTextStyle())
            .setShowWhen(false)
            .build()
    }

    private fun buildPermissionNotification(): Notification {
        val intent = Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS)
            .apply {
                data = Uri.fromParts("package", packageName, null)
            }
        val pendingIntent = PendingIntent.getActivity(
            this, 0, intent,
            PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
        )
        return NotificationCompat.Builder(this, HEALTH_CHANNEL)
            .setSmallIcon(R.drawable.ic_stats_steps)
            .setContentTitle(PERMISSION_TITLE)
            .setContentText(PERMISSION_TEXT)
            .setContentIntent(pendingIntent)
            .setShowWhen(false)
            .setAutoCancel(true)
            .build()
    }

    companion object {
        const val HEALTH_CHANNEL = "health_channel"
        private const val FOREGROUND_ID = 1
        private const val PERMISSION_ID = 2
        private const val TAG = "TrackingService"
        private const val FOREGROUND_TEXT =
            "PokéFit is running in background to measure your health data."
        private const val PERMISSION_TITLE = "Permission Required"
        private const val PERMISSION_TEXT =
            "PokéFit needs activity recognition permission to track your steps."
    }

}