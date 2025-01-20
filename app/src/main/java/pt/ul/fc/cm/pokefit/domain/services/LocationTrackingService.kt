package pt.ul.fc.cm.pokefit.domain.services

import android.app.Service
import android.content.Intent
import android.os.IBinder
import android.os.Looper
import android.location.Location
import androidx.core.app.NotificationCompat
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationCallback
import com.google.android.gms.location.LocationRequest
import com.google.android.gms.location.LocationResult
import com.google.android.gms.location.LocationServices
import com.google.android.gms.location.Priority
import com.google.android.gms.tasks.CancellationTokenSource
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import pt.ul.fc.cm.pokefit.R
import pt.ul.fc.cm.pokefit.domain.services.processors.LocationProcessor
import javax.inject.Inject

@AndroidEntryPoint
class LocationTrackingService : Service() {

    @Inject
    lateinit var locationProcessor: LocationProcessor // Inject the processor

    private lateinit var fusedLocationClient: FusedLocationProviderClient
    private lateinit var locationCallback: LocationCallback

    companion object {
        private val _isTracking = MutableStateFlow(false)
        val isTracking: StateFlow<Boolean> get() = _isTracking

        private val _currentLocation = MutableStateFlow<Location?>(null)
        val currentLocation: StateFlow<Location?> = _currentLocation.asStateFlow()
        const val ACTION_GET_CURRENT_LOCATION = "action_get_current_location"

        private const val CHANNEL_ID = "location_tracking_channel"
        private const val NOTIFICATION_ID = 1
    }

    override fun onCreate() {
        super.onCreate()
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this)

        locationCallback = object : LocationCallback() {
            override fun onLocationResult(locationResult: LocationResult) {
                locationResult.locations.forEach { location ->
                    _currentLocation.value = location
                    saveRoutePoint(location)
                }
            }
        }
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        when (intent?.action) {
            ACTION_GET_CURRENT_LOCATION -> {
                requestSingleLocation()
                return START_NOT_STICKY
            }
            else -> {
                _isTracking.value = true
                startForegroundService()
                startLocationUpdates()
                return START_STICKY
            }
        }
    }

    private fun requestSingleLocation() {
        try {
            fusedLocationClient.getCurrentLocation(
                Priority.PRIORITY_HIGH_ACCURACY,
                CancellationTokenSource().token
            ).addOnSuccessListener { location ->
                location?.let {
                    _currentLocation.value = it
                }
            }
        } catch (e: SecurityException) {
            e.printStackTrace()
        }
    }

    private fun startForegroundService() {
        val notification = NotificationCompat.Builder(this, CHANNEL_ID)
            .setContentTitle("Tracking Location")
            .setContentText("Your location is being tracked.")
            .setSmallIcon(R.drawable.ic_happy_face)
            .setPriority(NotificationCompat.PRIORITY_DEFAULT)
            .build()

        startForeground(NOTIFICATION_ID, notification)
    }

    private fun startLocationUpdates() {
        val locationRequest = LocationRequest.Builder(
            Priority.PRIORITY_HIGH_ACCURACY,
            5000L // Interval of 5 seconds
        ).apply {
            setMinUpdateIntervalMillis(2000L) // Minimum interval of 2 seconds
            setWaitForAccurateLocation(true)
        }.build()

        try {
            fusedLocationClient.requestLocationUpdates(locationRequest, locationCallback, Looper.getMainLooper())
        } catch (e: SecurityException) {
            e.printStackTrace()
        }
    }

    private fun saveRoutePoint(location: Location) {
        CoroutineScope(Dispatchers.IO).launch {
            locationProcessor.saveRoutePoint(location) // Delegate to processor
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _isTracking.value = false
        _currentLocation.value = null
        fusedLocationClient.removeLocationUpdates(locationCallback)
    }

    override fun onBind(intent: Intent?): IBinder? = null
}

