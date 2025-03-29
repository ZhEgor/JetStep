package com.zhiro.jetstep.core.locomotion.classifier.utils.algorithms


fun List<com.zhiro.jetstep.core.locomotion.classifier.model.step2.AccelerometerMagnitude>.mapLowPassFilter(alpha: Float): List<com.zhiro.jetstep.core.locomotion.classifier.model.step2.AccelerometerMagnitude> {
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

