package com.zhiro.jetstep.core.common.utils

import java.time.LocalDate
import java.time.LocalDateTime

fun LocalDate.atEndOfDay(): LocalDateTime {
    return plusDays(1).atStartOfDay().minusSeconds(1)
}
