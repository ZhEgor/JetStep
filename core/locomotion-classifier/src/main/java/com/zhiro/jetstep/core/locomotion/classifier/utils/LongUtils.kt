package com.zhiro.jetstep.core.locomotion.classifier.utils

import kotlin.math.abs

fun Long.round(decimals: Int): Long {
    var multiplier = 1.0
    repeat(abs(decimals)) { multiplier *= 10 }

    return (kotlin.math.round(this / multiplier) * multiplier).toLong()
}
