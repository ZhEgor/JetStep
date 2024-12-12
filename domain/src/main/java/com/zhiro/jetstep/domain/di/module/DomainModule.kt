package com.zhiro.jetstep.domain.di.module

import com.zhiro.jetstep.datasource.di.module.dataModule
import com.zhiro.jetstep.domain.repository.SensorRepository
import com.zhiro.jetstep.domain.repository.SensorRepositoryImpl
import com.zhiro.jetstep.domain.repository.StepRepository
import com.zhiro.jetstep.domain.repository.StepRepositoryImpl
import org.koin.core.definition.BeanDefinition
import org.koin.core.module.dsl.bind
import org.koin.core.module.dsl.factoryOf
import org.koin.dsl.module

val domainModule
    get() = module {
        includes(dataModule)

        // Repository set up
        factoryOf(::SensorRepositoryImpl, BeanDefinition<SensorRepository>::bind)
        factoryOf(::StepRepositoryImpl, BeanDefinition<StepRepository>::bind)
    }
