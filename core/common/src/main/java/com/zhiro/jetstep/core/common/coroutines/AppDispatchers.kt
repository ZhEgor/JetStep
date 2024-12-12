package com.zhiro.jetstep.core.common.coroutines

import kotlinx.coroutines.CoroutineDispatcher

interface AppDispatchers {

    val default: CoroutineDispatcher
    val main: CoroutineDispatcher
    val io: CoroutineDispatcher

}
