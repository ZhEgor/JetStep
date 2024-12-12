package com.zhiro.jetstep.feature.dashboard.di.module

import com.zhiro.jetstep.feature.dashboard.presentation.dashboard.DashboardViewModel
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.module

internal val viewModelModule
    get() = module {
        viewModelOf(::DashboardViewModel)
    }
