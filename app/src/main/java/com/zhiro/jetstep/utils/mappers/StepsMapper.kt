package com.zhiro.jetstep.utils.mappers

import com.zhiro.jetstep.domain.model.StepBatch
import com.zhiro.jetstep.domain.model.StepType
import com.zhiro.jetstep.core.locomotion.classifier.model.ClassifiedSteps

fun ClassifiedSteps.toStepBatch() = StepBatch(
    steps = steps.toInt(),
    startTime = startTime,
    endTime = endTime,
    type = StepType.getById(typeId.toInt()) ?: StepType.Walking
)
