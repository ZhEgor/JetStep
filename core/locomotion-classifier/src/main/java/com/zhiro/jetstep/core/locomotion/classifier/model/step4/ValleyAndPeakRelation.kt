package com.zhiro.jetstep.core.locomotion.classifier.model.step4

import kotlin.math.abs

data class ValleyAndPeakRelation(
    val peak: com.zhiro.jetstep.core.locomotion.classifier.model.step2.AccelerometerMagnitude,
    val valley: com.zhiro.jetstep.core.locomotion.classifier.model.step2.AccelerometerMagnitude,
    val duration: Long
) {
    companion object {

        fun create(
            peak: com.zhiro.jetstep.core.locomotion.classifier.model.step2.AccelerometerMagnitude,
            valley: com.zhiro.jetstep.core.locomotion.classifier.model.step2.AccelerometerMagnitude,
        ) = ValleyAndPeakRelation(
            peak = peak,
            valley = valley,
            duration = abs(peak.collectedAtMillis - valley.collectedAtMillis)
        )
    }
}
