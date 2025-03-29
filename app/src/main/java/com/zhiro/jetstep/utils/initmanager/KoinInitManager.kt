package com.zhiro.jetstep.utils.initmanager

import android.app.Application
import com.zhiro.jetstep.core.common.di.module.commonModule
import com.zhiro.jetstep.core.navigation.di.modules.navigationModule
import com.zhiro.jetstep.domain.di.module.domainModule
import com.zhiro.jetstep.feature.dashboard.di.module.featureDashboardModule
import com.zhiro.jetstep.core.locomotion.classifier.di.module.featureLocomotionHelperModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class KoinInitManager(private val application: Application) : InitManager {

    override fun init() {
        startKoin {
            androidContext(application)
            modules(
                domainModule,
                commonModule,
                navigationModule,
                featureDashboardModule,
                featureLocomotionHelperModule
            )
        }
    }
}
