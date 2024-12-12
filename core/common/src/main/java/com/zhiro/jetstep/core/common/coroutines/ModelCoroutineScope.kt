package com.zhiro.jetstep.core.common.coroutines

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.SupervisorJob
import kotlin.coroutines.CoroutineContext

class ModelCoroutineScope(val dispatchers: AppDispatchers) : CoroutineScope {
    override val coroutineContext: CoroutineContext = SupervisorJob() + dispatchers.default
}
