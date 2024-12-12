package com.zhiro.jetstep.feature.dashboard.di.module

import com.zhiro.jetstep.feature.dashboard.presentation.dashboard.usecase.LoadStepBatchesUseCase
import com.zhiro.jetstep.feature.dashboard.presentation.dashboard.usecase.SaveStepBatchManuallyUseCase
import org.koin.core.module.dsl.factoryOf
import org.koin.dsl.module

internal val useCaseModule
    get() = module {
        factoryOf(::LoadStepBatchesUseCase)
        factoryOf(::SaveStepBatchManuallyUseCase)
    }
