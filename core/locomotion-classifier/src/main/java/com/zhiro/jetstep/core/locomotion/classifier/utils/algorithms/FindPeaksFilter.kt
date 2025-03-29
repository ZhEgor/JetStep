package com.zhiro.jetstep.core.locomotion.classifier.utils.algorithms

import java.util.stream.IntStream.range

fun List<com.zhiro.jetstep.core.locomotion.classifier.model.step2.AccelerometerMagnitude>.findPeakSimple(
    threshold: Float,
    reversed: Boolean = false,
): List<com.zhiro.jetstep.core.locomotion.classifier.model.step2.AccelerometerMagnitude> {
    val filteredValues = mutableListOf<com.zhiro.jetstep.core.locomotion.classifier.model.step2.AccelerometerMagnitude>()
    val coef = if (reversed) -1 else 1

    @Suppress("NAME_SHADOWING")
    val threshold = threshold * coef

    for (index in range(0, size)) {
        val prev = (getOrNull(index - 1)?.magnitude ?: Float.MIN_VALUE) * coef
        val current = get(index).magnitude * coef
        val next = (getOrNull(index + 1)?.magnitude ?: Float.MIN_VALUE) * coef

        when {
            prev > current && current < next && current <= threshold -> {
                filteredValues.add(get(index))
            }

            else -> continue
        }
    }

    return filteredValues
}
