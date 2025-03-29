package com.zhiro.jetstep.core.locomotion.classifier.utils.algorithms

fun List<com.zhiro.jetstep.core.locomotion.classifier.model.step2.AccelerometerMagnitude>.findLocalPeaksAndValleysFilter(
    intervalDurationWindow: Long
): List<com.zhiro.jetstep.core.locomotion.classifier.model.step2.AccelerometerMagnitude> {
    if (isEmpty()) return emptyList()
    val startTimestamp = first().collectedAtMillis
    val endTimestamp = last().collectedAtMillis
    val localPeaksAndValleys = mutableListOf<com.zhiro.jetstep.core.locomotion.classifier.model.step2.AccelerometerMagnitude>()

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
            val localMin = local.minBy(com.zhiro.jetstep.core.locomotion.classifier.model.step2.AccelerometerMagnitude::magnitude)
            val localMax = local.maxBy(com.zhiro.jetstep.core.locomotion.classifier.model.step2.AccelerometerMagnitude::magnitude)

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
