package pt.ul.fc.cm.pokefit

import android.app.Application
import android.app.NotificationChannel
import android.app.NotificationManager
import dagger.hilt.android.HiltAndroidApp
import pt.ul.fc.cm.pokefit.domain.service.TrackingService.Companion.HEALTH_CHANNEL

@HiltAndroidApp
class PokeFitApplication: Application() {

    override fun onCreate() {
        super.onCreate()
        val name = "Health Tracking"
        val descriptionText = "Tracks your health activity in the background."
        val importance = NotificationManager.IMPORTANCE_DEFAULT
        val channel = NotificationChannel(HEALTH_CHANNEL, name, importance).apply {
                description = descriptionText
        }
        channel.setShowBadge(false)
        val notificationManager =
            getSystemService(NOTIFICATION_SERVICE) as NotificationManager
        notificationManager.createNotificationChannel(channel)
    }

}