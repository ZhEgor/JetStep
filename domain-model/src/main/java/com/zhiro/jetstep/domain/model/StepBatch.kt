package com.zhiro.jetstep.domain.model

import com.zhiro.jetstep.datasource.room.models.StepBatchEntity

data class StepBatch(
    val steps: Int,
    val startTime: Long,
    val endTime: Long,
    val type: StepType
)

val StepBatch.duration get() = endTime - startTime

fun List<StepBatch>.aggregate(): Map<StepType, Int> {
    var walkingSteps = 0
    var joggingSteps = 0
    var runningSteps = 0

    forEach { batch ->
        when (batch.type) {
            StepType.Walking -> {
                walkingSteps += batch.steps
            }
            StepType.Jogging -> {
                joggingSteps += batch.steps
            }
            StepType.Running -> {
                runningSteps += batch.steps
            }
        }
    }

    return mapOf(
        StepType.Walking to walkingSteps,
        StepType.Jogging to joggingSteps,
        StepType.Running to runningSteps,
    )
}

fun StepBatchEntity.toDomain() = StepBatch(
    steps = steps,
    startTime = startTime,
    endTime = endTime,
    type = StepType.getById(id = typeId) ?: StepType.Walking
)

fun StepBatch.toData() = StepBatchEntity(
    steps = steps,
    startTime = startTime,
    endTime = endTime,
    typeId = type.id
)
