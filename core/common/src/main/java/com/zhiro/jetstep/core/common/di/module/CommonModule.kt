package com.zhiro.jetstep.core.common.di.module

import com.zhiro.jetstep.core.common.coroutines.AndroidAppDispatchers
import com.zhiro.jetstep.core.common.coroutines.AppDispatchers
import com.zhiro.jetstep.core.common.coroutines.ModelCoroutineScope
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import org.koin.core.definition.BeanDefinition
import org.koin.core.module.dsl.bind
import org.koin.core.module.dsl.factoryOf
import org.koin.dsl.module

val commonModule
    get() = module {
        factoryOf(::AndroidAppDispatchers, BeanDefinition<AppDispatchers>::bind)
        factoryOf(::ModelCoroutineScope)
        factory<CoroutineScope> {
            CoroutineScope(SupervisorJob() + Dispatchers.Default)
        }
    }
