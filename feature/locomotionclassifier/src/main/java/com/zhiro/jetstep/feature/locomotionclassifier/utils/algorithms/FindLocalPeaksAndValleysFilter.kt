package com.zhiro.jetstep.feature.locomotionclassifier.utils.algorithms

import com.zhiro.jetstep.feature.locomotionclassifier.model.step2.AccelerometerMagnitude

fun List<AccelerometerMagnitude>.findLocalPeaksAndValleysFilter(
    intervalDurationWindow: Long
): List<AccelerometerMagnitude> {
    if (isEmpty()) return emptyList()
    val startTimestamp = first().collectedAtMillis
    val endTimestamp = last().collectedAtMillis
    val localPeaksAndValleys = mutableListOf<AccelerometerMagnitude>()

    var localEndTimestamp = startTimestamp
    while (startTimestamp <= endTimestamp) {
        val localStartTimestamp = localEndTimestamp + 1
        localEndTimestamp = localStartTimestamp + intervalDurationWindow

        val local = filter { mag ->
            mag.collectedAtMillis in localStartTimestamp..localEndTimestamp
        }

        if (local.isEmpty()) {
            val nextLocalStartTimestamp = firstOrNull { mag ->
                localEndTimestamp < mag.collectedAtMillis
            }?.collectedAtMillis
            if (nextLocalStartTimestamp == null) {
                break
            } else {
                localEndTimestamp = nextLocalStartTimestamp
            }
        } else {
            val localMin = local.minBy(AccelerometerMagnitude::magnitude)
            val localMax = local.maxBy(AccelerometerMagnitude::magnitude)

            if (localMin.collectedAtMillis < localMax.collectedAtMillis) {
                localPeaksAndValleys.add(localMin)
                localPeaksAndValleys.add(localMax)
            } else {
                localPeaksAndValleys.add(localMax)
                localPeaksAndValleys.add(localMin)
            }
        }
    }
    return localPeaksAndValleys
}
