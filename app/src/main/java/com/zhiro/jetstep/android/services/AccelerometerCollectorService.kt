package com.zhiro.jetstep.android.services

import android.app.Service
import android.content.Intent
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import android.os.Binder
import android.os.IBinder
import androidx.compose.runtime.mutableStateOf
import com.zhiro.jetstep.domain.model.RawAccelerometerRecord
import com.zhiro.jetstep.domain.repository.SensorRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.launch
import org.koin.android.ext.android.inject
import java.util.concurrent.CopyOnWriteArrayList
import kotlin.math.sqrt

var latestValue = mutableStateOf(0.0f)

class AccelerometerCollectorService : Service(), SensorEventListener {

    private var sensorManager: SensorManager? = null
    private var accelerometerRecords = CopyOnWriteArrayList(mutableListOf<RawAccelerometerRecord>())
    private val scope = CoroutineScope(SupervisorJob() + Dispatchers.IO)
    private val sensorRepository: SensorRepository by inject()
    private val binder = AccelerometerCollectorBinder()
    private var pace: String = ""

    override fun onBind(intent: Intent?): IBinder {
        pace = intent?.getStringExtra(PACE_ARG) ?: ""
        sensorManager = getSystemService(SENSOR_SERVICE) as SensorManager
        sensorManager?.getDefaultSensor(Sensor.TYPE_ACCELEROMETER)?.also { sensor ->
            sensorManager?.registerListener(this, sensor, SAMPLING_RATE_MICROSECONDS)
        }
        return binder
    }

    suspend fun finish() {
        sensorManager?.unregisterListener(this)
        sensorManager = null
        sync()
    }

    override fun onSensorChanged(event: SensorEvent?) {
        if (event?.sensor?.type == Sensor.TYPE_ACCELEROMETER) {
            accelerometerRecords.add(
                RawAccelerometerRecord(
                    x = event.values[0],
                    y = event.values[1],
                    z = event.values[2],
                    recordTakenAt = System.currentTimeMillis(),
                    pace = pace
                )
            )
            val last = accelerometerRecords.last()!!
            latestValue.value = sqrt(last.x * last.x + last.y * last.y + last.z * last.z)
            syncIfNeeded()
        }
    }

    override fun onAccuracyChanged(p0: Sensor?, p1: Int) {}

    suspend fun sync() {
        val accelerometerRecordsToSync = accelerometerRecords
        sensorRepository.saveRawAccelerometerRecords(records = accelerometerRecordsToSync)
        accelerometerRecords.removeAll(accelerometerRecordsToSync)
    }

    private fun syncIfNeeded() {
        if (accelerometerRecords.size > SYNC_IS_NEEDED_LIMIT) {
            scope.launch {
                sync()
            }
        }
    }

    inner class AccelerometerCollectorBinder : Binder() {
        // Return this instance of LocalService so clients can call public methods.
        fun getService(): AccelerometerCollectorService = this@AccelerometerCollectorService
    }

    companion object {
        const val PACE_ARG = "pace_arg"
        private const val SYNC_IS_NEEDED_LIMIT = 500
        private const val SAMPLING_RATE_MICROSECONDS = 20_000 // 50 HZ
    }
}

