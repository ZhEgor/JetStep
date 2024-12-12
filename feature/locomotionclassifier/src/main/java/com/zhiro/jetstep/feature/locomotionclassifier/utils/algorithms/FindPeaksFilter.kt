package com.zhiro.jetstep.feature.locomotionclassifier.utils.algorithms

import com.zhiro.jetstep.feature.locomotionclassifier.model.step2.AccelerometerMagnitude
import java.util.stream.IntStream.range

fun List<AccelerometerMagnitude>.findPeakSimple(
    threshold: Float,
    reversed: Boolean = false,
): List<AccelerometerMagnitude> {
    val filteredValues = mutableListOf<AccelerometerMagnitude>()
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
