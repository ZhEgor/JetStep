package com.zhiro.jetstep.feature.locomotionclassifier.model.step5

import com.zhiro.jetstep.feature.locomotionclassifier.model.step4.ValleyAndPeakRelation


data class StepFeature(
    val magnitude: Float,
    val peakValleyIntervalDuration: Long,
    val currentPreviousPeakIntervalDuration: Long,
    val timestamp: Long
) {

    companion object {

        fun create(
            prevValleyAndPeakRelation: ValleyAndPeakRelation,
            currentValleyAndPeakRelation: ValleyAndPeakRelation,
        ) = StepFeature(
            magnitude = currentValleyAndPeakRelation.peak.magnitude,
            peakValleyIntervalDuration = currentValleyAndPeakRelation.duration,
            currentPreviousPeakIntervalDuration = currentValleyAndPeakRelation.peak.collectedAtMillis -
                prevValleyAndPeakRelation.peak.collectedAtMillis,
            timestamp = currentValleyAndPeakRelation.peak.collectedAtMillis
        )
    }
}

fun List<StepFeature>.toFloatArray() = map { stepFeature ->
    listOf(
        stepFeature.magnitude,
        stepFeature.peakValleyIntervalDuration.toFloat(),
        stepFeature.currentPreviousPeakIntervalDuration.toFloat()
    )
}.flatten().toFloatArray()