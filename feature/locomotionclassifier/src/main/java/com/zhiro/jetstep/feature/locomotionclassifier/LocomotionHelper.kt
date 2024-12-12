package com.zhiro.jetstep.feature.locomotionclassifier

import androidx.compose.runtime.mutableStateOf
import com.zhiro.jetstep.core.common.coroutines.ModelCoroutineScope
import com.zhiro.jetstep.feature.locomotionclassifier.model.ClassifiedSteps
import com.zhiro.jetstep.feature.locomotionclassifier.model.step1.record.TimestampAccelerometerRecord
import com.zhiro.jetstep.feature.locomotionclassifier.model.step1.record.TimestampStepRecord
import com.zhiro.jetstep.feature.locomotionclassifier.model.step2.StepsDetails
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.sync.Mutex
import kotlinx.coroutines.sync.withLock
import kotlin.time.Duration.Companion.seconds

var secondsValue = mutableStateOf(
    0L
)

interface LocomotionHelper {

    val lastClassifiedStepsFlow: SharedFlow<ClassifiedSteps>

    fun feedSteps(aggregatedStepsCount: Long)

    fun feedAccelerometer(x: Float, y: Float, z: Float)
}

internal class LocomotionHelperImpl(
    private val coroutineScope: ModelCoroutineScope,
    private val locomotionClassifier: LocomotionClassifier
) : LocomotionHelper {

    override val lastClassifiedStepsFlow = MutableSharedFlow<ClassifiedSteps>()
    private val timestampStepRecords = mutableListOf<TimestampStepRecord>()
    private val timestampAccelerometerRecords = mutableListOf<TimestampAccelerometerRecord>()
    private var lastTimeAggregatedAtMillis = System.currentTimeMillis()
    private val mutex = Mutex()

    override fun feedSteps(aggregatedStepsCount: Long) {
        // assign time to a record and save
        coroutineScope.launch {
            val timestampStep =
                TimestampStepRecord.create(aggregatedStepsCount = aggregatedStepsCount)
            mutex.withLock(this) {
                timestampStepRecords.add(timestampStep)
            }
            startAggregationIfRequired()
        }
    }

    override fun feedAccelerometer(x: Float, y: Float, z: Float) {
        // assign time to a record and save
        coroutineScope.launch {
            val timestampAccelerometerRecord =
                TimestampAccelerometerRecord.create(x = x, y = y, z = z)
            mutex.withLock(this) {
                timestampAccelerometerRecords.add(timestampAccelerometerRecord)
            }
            startAggregationIfRequired()
        }
    }

    /**
     * starts aggregation every [AGGREGATION_COOLDOWN]
     */
    private suspend fun startAggregationIfRequired() {
        secondsValue.value = (System.currentTimeMillis() - lastTimeAggregatedAtMillis) / 1000
        if (System.currentTimeMillis() - lastTimeAggregatedAtMillis > AGGREGATION_COOLDOWN.inWholeMilliseconds) {

            // start aggregation
            var tempTimestampStepRecords: List<TimestampStepRecord>
            var tempTimestampAccelerometerRecords: List<TimestampAccelerometerRecord>
            mutex.withLock {
                tempTimestampAccelerometerRecords = timestampAccelerometerRecords.toList()
                tempTimestampStepRecords = timestampStepRecords.toList()
                timestampAccelerometerRecords.clear()
                timestampStepRecords.clear()
                // update lastTimeAggregatedAtMillis and release the lock
                lastTimeAggregatedAtMillis = System.currentTimeMillis()
            }
            val stepsDetails = StepsDetails.create(
                timestampStepRecords = tempTimestampStepRecords,
                timestampAccelerometerRecords = tempTimestampAccelerometerRecords
            )
            val classifiedStep = locomotionClassifier.predict(stepsDetails = stepsDetails)
            if (classifiedStep != null) {
                lastClassifiedStepsFlow.emit(classifiedStep)
            }
        }
    }

    companion object {
        private val AGGREGATION_COOLDOWN = 60.seconds
    }
}


