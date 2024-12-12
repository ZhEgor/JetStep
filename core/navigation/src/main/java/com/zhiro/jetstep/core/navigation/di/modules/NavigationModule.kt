package com.zhiro.jetstep.core.navigation.di.modules

import com.zhiro.jetstep.core.navigation.domain.NavigationManager
import com.zhiro.jetstep.core.navigation.domain.NavigationManagerImpl
import org.koin.dsl.module

val navigationModule
    get() = module {
        single<NavigationManager> {
            NavigationManagerImpl()
        }
    }
