package com.zhiro.jetstep.core.locomotion.classifier.utils.algorithms

import com.zhiro.jetstep.core.locomotion.classifier.model.step4.ValleyAndPeakRelation
import com.zhiro.jetstep.core.locomotion.classifier.model.step5.StepFeature
import com.zhiro.jetstep.core.locomotion.classifier.utils.round
import java.util.stream.IntStream.range

fun List<ValleyAndPeakRelation>.toFeatures(error: Long): List<StepFeature> {
    val stepFeatures = mutableListOf<StepFeature>()
    // creating features
    for (index in range(0, size)) {
        val prev = getOrNull(index - 1)
        val current = get(index)
        if (prev != null && current.peak.collectedAtMillis - prev.peak.collectedAtMillis  > 0) {
            stepFeatures.add(
                StepFeature.create(
                    prevValleyAndPeakRelation = prev,
                    currentValleyAndPeakRelation = current
                )
            )
        }
    }

    // finding step interval duration mode
    val peaksIntervalDurationMode = stepFeatures.mode { stepFeature ->
        stepFeature.currentPreviousPeakIntervalDuration.round(2)
    } ?: Long.MIN_VALUE

    // filtering features
    val filteredStepFeatures = stepFeatures.filter { stepFeature ->
        stepFeature.currentPreviousPeakIntervalDuration >= peaksIntervalDurationMode - error &&
            stepFeature.currentPreviousPeakIntervalDuration <= peaksIntervalDurationMode + error
    }


    return filteredStepFeatures
}
