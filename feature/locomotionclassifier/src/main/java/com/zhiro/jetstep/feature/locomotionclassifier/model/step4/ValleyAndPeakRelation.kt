package com.zhiro.jetstep.feature.locomotionclassifier.model.step4

import com.zhiro.jetstep.feature.locomotionclassifier.model.step2.AccelerometerMagnitude
import kotlin.math.abs

data class ValleyAndPeakRelation(
    val peak: AccelerometerMagnitude,
    val valley: AccelerometerMagnitude,
    val duration: Long
) {
    companion object {

        fun create(
            peak: AccelerometerMagnitude,
            valley: AccelerometerMagnitude,
        ) = ValleyAndPeakRelation(
            peak = peak,
            valley = valley,
            duration = abs(peak.collectedAtMillis - valley.collectedAtMillis)
        )
    }
}
