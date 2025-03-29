package com.zhiro.jetstep.core.locomotion.classifier.model

import com.zhiro.jetstep.core.locomotion.classifier.model.step2.StepsDetails

data class ClassifiedSteps(
    val steps: Long,
    val typeId: Long,
    val startTime: Long,
    val endTime: Long,
) {

    companion object {

        internal fun create(stepsDetails: StepsDetails, typeId: Long) = ClassifiedSteps(
            typeId = typeId,
            steps = stepsDetails.stepsAmount,
            startTime = stepsDetails.accelerometerRecords.first().collectedAtMillis,
            endTime = stepsDetails.accelerometerRecords.last().collectedAtMillis
        )
    }
}
