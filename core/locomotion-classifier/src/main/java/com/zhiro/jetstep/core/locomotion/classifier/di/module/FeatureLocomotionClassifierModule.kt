package com.zhiro.jetstep.core.locomotion.classifier.di.module

import com.zhiro.jetstep.core.locomotion.classifier.LocomotionClassifier
import com.zhiro.jetstep.core.locomotion.classifier.LocomotionClassifierImpl
import com.zhiro.jetstep.core.locomotion.classifier.LocomotionHelper
import com.zhiro.jetstep.core.locomotion.classifier.LocomotionHelperImpl
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
