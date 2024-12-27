package pt.ul.fc.cm.pokefit.presentation.screens.home

import android.Manifest
import android.app.Application
import android.content.Context
import android.content.pm.PackageManager
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import android.util.Log
import androidx.compose.runtime.mutableStateOf
import androidx.core.content.ContextCompat
import androidx.lifecycle.AndroidViewModel

class HomeScreenViewModel(application: Application) : AndroidViewModel(application), SensorEventListener {
    private val context = application.applicationContext
    private val sensorManager = application.getSystemService(Context.SENSOR_SERVICE) as SensorManager
    private val stepCounterSensor = sensorManager.getDefaultSensor(Sensor.TYPE_STEP_COUNTER)

    val steps = mutableStateOf(0) // State to track the number of steps
    private var initialStepCount = -1

    init {
        if (ContextCompat.checkSelfPermission(
                context,
                Manifest.permission.ACTIVITY_RECOGNITION
            ) == PackageManager.PERMISSION_GRANTED
        ) {
            stepCounterSensor?.let {
                sensorManager.registerListener(this, it, SensorManager.SENSOR_DELAY_UI)
            }
        } else {
            Log.e("HomeViewModel", "Permission not granted. Step tracking unavailable.")
        }
    }

    override fun onSensorChanged(event: SensorEvent?) {
        event?.let {
            if (initialStepCount == -1) {
                initialStepCount = it.values[0].toInt() // Set the initial count
            }
            steps.value = it.values[0].toInt() - initialStepCount // Calculate steps
        }
    }

    override fun onAccuracyChanged(sensor: Sensor?, accuracy: Int) {
        // Not needed for this use case
    }

    override fun onCleared() {
        super.onCleared()
        sensorManager.unregisterListener(this) // Unregister the listener
    }
}
