package com.zhiro.jetstep.core.ui.base.mvi.util

import java.util.UUID

data class BaseError(
    val message: String?,
    val key: String = UUID.randomUUID().toString()
)

fun Throwable.toError() = BaseError(message = message)
