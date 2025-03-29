package com.zhiro.jetstep.core.locomotion.classifier.utils.algorithms

import com.zhiro.jetstep.core.locomotion.classifier.model.step4.ValleyAndPeakRelation


fun List<com.zhiro.jetstep.core.locomotion.classifier.model.step2.AccelerometerMagnitude>.findRelatedPeaksAndValleysFilter(
    intervalDurationWindow: Long,
    valleyPeakDurationWindow: Long,
    lowerThreshold: Float,
    upperThreshold: Float,
): List<ValleyAndPeakRelation> {
    if (isEmpty()) return emptyList()
    val startTimestamp = first().collectedAtMillis
    val endTimestamp = last().collectedAtMillis
    val valleyAndPeakRelations = mutableListOf<ValleyAndPeakRelation>()

    var localEndTimestamp = startTimestamp
    while (startTimestamp <= endTimestamp) {
        val localStartTimestamp = localEndTimestamp + 1
        localEndTimestamp = localStartTimestamp + intervalDurationWindow

        val localValleys = filter { mag ->
            mag.collectedAtMillis in localStartTimestamp..localEndTimestamp && mag.magnitude <= lowerThreshold
        }
        val localMin = localValleys.minByOrNull(com.zhiro.jetstep.core.locomotion.classifier.model.step2.AccelerometerMagnitude::magnitude)

        if (localMin == null) {
            val nextLocalStartTimestamp = firstOrNull { mag ->
                localEndTimestamp < mag.collectedAtMillis
            }?.collectedAtMillis
            if (nextLocalStartTimestamp == null) {
                break
            } else {
                localEndTimestamp = nextLocalStartTimestamp
                continue
            }
        }

        val localPeaks = filter { mag ->
            mag.collectedAtMillis in (
                localMin.collectedAtMillis + 1..localMin.collectedAtMillis + valleyPeakDurationWindow
                ) && mag.magnitude >= upperThreshold
        }
        val localMax = localPeaks.maxByOrNull(com.zhiro.jetstep.core.locomotion.classifier.model.step2.AccelerometerMagnitude::magnitude)

        if (localMax != null) {
            val valleyAndPeakRelation = ValleyAndPeakRelation.create(
                peak = localMax,
                valley = localMin
            )
            valleyAndPeakRelations.add(valleyAndPeakRelation)
        }
    }
    return valleyAndPeakRelations
}