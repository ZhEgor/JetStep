package com.zhiro.jetstep.feature.locomotionclassifier.utils.algorithms

import com.zhiro.jetstep.feature.locomotionclassifier.model.step2.AccelerometerMagnitude


fun List<AccelerometerMagnitude>.mapLowPassFilter(alpha: Float): List<AccelerometerMagnitude> {
    return mapIndexed { index, accelerometerMagnitude ->
        if (index == 0) {
            accelerometerMagnitude
        } else {
            accelerometerMagnitude.copy(
                magnitude = (alpha * get(index - 1).magnitude) + (1 - alpha) * accelerometerMagnitude.magnitude
            )
        }
    }
}

