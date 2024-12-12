package com.zhiro.jetstep.android.services

import android.annotation.SuppressLint
import android.app.Service
import android.content.Intent
import android.content.pm.ServiceInfo
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import android.os.Build
import android.os.IBinder
import androidx.core.app.ServiceCompat
import com.zhiro.jetstep.android.notifications.NotificationsHelper
import com.zhiro.jetstep.domain.repository.StepRepository
import com.zhiro.jetstep.feature.locomotionclassifier.LocomotionHelper
import com.zhiro.jetstep.feature.locomotionclassifier.model.ClassifiedSteps
import com.zhiro.jetstep.utils.mappers.toStepBatch
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.cancel
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import org.koin.android.ext.android.inject

class FitnessForegroundService : Service(), SensorEventListener {

    val scope = CoroutineScope(Dispatchers.IO + SupervisorJob())
    private var sensorManager: SensorManager? = null
    private val locomotionHelper: LocomotionHelper by inject()
    private val stepRepository: StepRepository by inject()

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        startAsForegroundService()
        registerSensors()
        observeClassifiedSteps()
        return super.onStartCommand(intent, flags, startId)
    }

    private fun observeClassifiedSteps() {
        scope.launch {
            locomotionHelper.lastClassifiedStepsFlow.map(ClassifiedSteps::toStepBatch).collect { stepBatch ->
                stepRepository.saveStepBatch(stepBatch)
            }
        }
    }

    fun registerSensors() {
        sensorManager = getSystemService(SENSOR_SERVICE) as SensorManager
        sensorManager?.getDefaultSensor(Sensor.TYPE_ACCELEROMETER)?.also { sensor ->
            sensorManager?.registerListener(this, sensor, SAMPLING_RATE_MICROSECONDS)
        }
        sensorManager?.getDefaultSensor(Sensor.TYPE_STEP_COUNTER)?.also { sensor ->
            sensorManager?.registerListener(this, sensor, SAMPLING_RATE_MICROSECONDS)
        }
    }

    private fun unregisterSensors() {
        sensorManager?.unregisterListener(this)
        sensorManager = null
    }

    override fun onSensorChanged(event: SensorEvent?) {
        when (event?.sensor?.type) {
            Sensor.TYPE_ACCELEROMETER -> locomotionHelper.feedAccelerometer(
                x = event.values[0],
                y = event.values[1],
                z = event.values[2]
            )

            Sensor.TYPE_STEP_COUNTER -> locomotionHelper.feedSteps(
                aggregatedStepsCount = event.values[0].toLong()
            )
        }
    }

    @SuppressLint("ForegroundServiceType")
    private fun startAsForegroundService() {
        NotificationsHelper.createNotificationChannel(this)

        ServiceCompat.startForeground(
            this,
            FOREGROUND_SERVICE_ID,
            NotificationsHelper.buildNotification(this),
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
                ServiceInfo.FOREGROUND_SERVICE_TYPE_HEALTH
            } else {
                0
            }
        )
    }


    override fun onDestroy() {
        unregisterSensors()
        scope.cancel()
        super.onDestroy()
    }

    override fun onAccuracyChanged(event: Sensor?, p1: Int) {}

    override fun onBind(p0: Intent?): IBinder? = null

    companion object {
        private const val FOREGROUND_SERVICE_ID = 1
        private const val SAMPLING_RATE_MICROSECONDS = 20_000 // 50 HZ
    }
}
