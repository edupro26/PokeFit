package pt.ul.fc.cm.pokefit

import android.app.Application
import dagger.hilt.android.HiltAndroidApp
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context

@HiltAndroidApp
class PokeFitApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        createLocationTrackingChannel(this)
    }

    companion object {
        const val LOCATION_CHANNEL_ID = "location_tracking_channel"
    }
}

fun createLocationTrackingChannel(context: Context) {
    val name = "Location Tracking"
    val descriptionText = "Tracks your location in the background."
    val importance = NotificationManager.IMPORTANCE_DEFAULT
    val channel = NotificationChannel(PokeFitApplication.LOCATION_CHANNEL_ID, name, importance).apply {
        description = descriptionText
    }

    val notificationManager: NotificationManager =
        context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
    notificationManager.createNotificationChannel(channel)
}