package com.zhiro.jetstep.core.locomotion.classifier.model.step1.record

internal data class TimestampStepRecord(
    val aggregatedStepsCount: Long,
    val collectedAtMillis: Long
) {

    companion object {

        fun create(aggregatedStepsCount: Long) = TimestampStepRecord(
            aggregatedStepsCount = aggregatedStepsCount,
            collectedAtMillis = System.currentTimeMillis()
        )
    }
}
