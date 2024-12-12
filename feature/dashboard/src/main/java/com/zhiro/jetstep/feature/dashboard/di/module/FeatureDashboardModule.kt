package com.zhiro.jetstep.feature.dashboard.di.module

import org.koin.dsl.module

val featureDashboardModule
    get() = module {
        includes(useCaseModule, viewModelModule)
    }
