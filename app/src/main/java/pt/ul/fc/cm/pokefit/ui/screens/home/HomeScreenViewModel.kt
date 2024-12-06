package pt.ul.fc.cm.pokefit.ui.screens.home

import android.app.Application
import android.content.Context
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.AndroidViewModel

class HomeViewModel(application: Application) : AndroidViewModel(application), SensorEventListener {
    private val sensorManager = application.getSystemService(Context.SENSOR_SERVICE) as SensorManager
    private val stepCounterSensor = sensorManager.getDefaultSensor(Sensor.TYPE_STEP_COUNTER)

    val steps = mutableStateOf(0) // State to track the number of steps
    private var initialStepCount = -1

    init {
        // Register the sensor listener
        stepCounterSensor?.let {
            sensorManager.registerListener(this, it, SensorManager.SENSOR_DELAY_UI)
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