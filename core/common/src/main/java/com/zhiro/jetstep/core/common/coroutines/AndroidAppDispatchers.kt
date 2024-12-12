package com.zhiro.jetstep.core.common.coroutines

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers

internal class AndroidAppDispatchers : AppDispatchers {

    override val default: CoroutineDispatcher
        get() = Dispatchers.Default
    override val main: CoroutineDispatcher
        get() = Dispatchers.Main
    override val io: CoroutineDispatcher
        get() = Dispatchers.IO
}
