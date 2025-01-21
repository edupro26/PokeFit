package pt.ul.fc.cm.pokefit.domain.sensor

import android.content.Context
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import android.util.Log

abstract class AndroidSensor(
    sensorType: Int,
    private val context: Context,
    private val sensorFeature: String
) : MeasurableSensor(sensorType), SensorEventListener {

    private lateinit var sensorManager: SensorManager
    private var sensor: Sensor? = null
    private var isActive = false

    override val sensorExists: Boolean
        get() = context.packageManager.hasSystemFeature(sensorFeature)

    override fun startListening() {
        if(!sensorExists) {
            return
        }
        if(!::sensorManager.isInitialized && sensor == null) {
            sensorManager = context.getSystemService(Context.SENSOR_SERVICE) as SensorManager
            sensor = sensorManager.getDefaultSensor(sensorType)
        }
        sensor?.let {
            sensorManager.registerListener(this, it, SensorManager.SENSOR_DELAY_UI)
            isActive = true
            Log.d("AndroidSensor", "Listening to sensor: $sensorType")
        }
    }

    override fun stopListening() {
        if(!sensorExists || !::sensorManager.isInitialized) {
            return
        }
        sensorManager.unregisterListener(this)
        isActive = false
        Log.d("AndroidSensor", "Stopped listening to sensor: $sensorType")
    }

    override fun isListening(): Boolean = isActive

    override fun onSensorChanged(event: SensorEvent?) {
        if(!sensorExists) {
            return
        }
        if(event?.sensor?.type == sensorType) {
            onSensorValuesChanged?.invoke(event.values.toList())
        }
    }

    override fun onAccuracyChanged(p0: Sensor?, p1: Int) = Unit
}