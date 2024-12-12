package com.zhiro.jetstep.domain.test.model

import com.zhiro.jetstep.domain.model.StepBatch
import com.zhiro.jetstep.domain.model.StepType

private const val MINUTE = 60 * 1000
private const val HOUR = 60 * 60 * 1000
val currentUnixTime = System.currentTimeMillis() // Get current Unix time

val fakeStepBatches1 = run {

    listOf(
        StepBatch(
            steps = 500,
            startTime = currentUnixTime, // Start from now
            endTime = currentUnixTime + 10 * MINUTE, // 10 minutes later
            type = StepType.Walking
        ),
        StepBatch(
            steps = 750,
            startTime = currentUnixTime + 20 * MINUTE, // 20 minutes later
            endTime = currentUnixTime + 30 * MINUTE,   // 30 minutes later
            type = StepType.Running
        ),
        StepBatch(
            steps = 200,
            startTime = currentUnixTime + 40 * MINUTE, // 40 minutes later
            endTime = currentUnixTime + 50 * MINUTE,   // 50 minutes later
            type = StepType.Jogging
        ),
        StepBatch(
            steps = 1000,
            startTime = currentUnixTime + 60 * MINUTE, // 1 hour later
            endTime = currentUnixTime + 10 * MINUTE + HOUR ,   // 1 hour and 10 minutes later
            type = StepType.Walking
        ),
        StepBatch(
            steps = 300,
            startTime = currentUnixTime + 20 * MINUTE + HOUR, // 1 hour 20 minutes later
            endTime = currentUnixTime + 30 * MINUTE + HOUR,   // 1 hour 30 minutes later
            type = StepType.Walking
        )
    )
}

val fakeStepBatches2 = run {

    listOf(
        StepBatch(
            steps = 1000,
            startTime = currentUnixTime + 30 * MINUTE + HOUR, // 1 hour 20 minutes later
            endTime = currentUnixTime + 50 * MINUTE + HOUR,   // 1 hour 30 minutes later
            type = StepType.Running
        ),
        StepBatch(
            steps = 500,
            startTime = currentUnixTime + 50 * MINUTE + HOUR, // 1 hour 20 minutes later
            endTime = currentUnixTime + 10 * MINUTE + 2 * HOUR,   // 1 hour 30 minutes later
            type = StepType.Jogging
        )
    )
}