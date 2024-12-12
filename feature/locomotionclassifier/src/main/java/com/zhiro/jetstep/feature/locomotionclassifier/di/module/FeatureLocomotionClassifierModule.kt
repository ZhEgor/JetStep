package com.zhiro.jetstep.feature.locomotionclassifier.di.module

import com.zhiro.jetstep.feature.locomotionclassifier.LocomotionClassifier
import com.zhiro.jetstep.feature.locomotionclassifier.LocomotionClassifierImpl
import com.zhiro.jetstep.feature.locomotionclassifier.LocomotionHelper
import com.zhiro.jetstep.feature.locomotionclassifier.LocomotionHelperImpl
import org.koin.core.definition.BeanDefinition
import org.koin.core.module.dsl.bind
import org.koin.core.module.dsl.factoryOf
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module

val featureLocomotionHelperModule
    get() = module {
        singleOf(::LocomotionHelperImpl, BeanDefinition<LocomotionHelper>::bind)
        factoryOf(::LocomotionClassifierImpl, BeanDefinition<LocomotionClassifier>::bind)
    }
