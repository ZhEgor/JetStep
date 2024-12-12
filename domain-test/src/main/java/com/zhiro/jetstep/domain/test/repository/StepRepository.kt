package com.zhiro.jetstep.domain.test.repository

import com.zhiro.jetstep.domain.model.StepBatch
import com.zhiro.jetstep.domain.repository.StepRepository
import com.zhiro.jetstep.domain.test.model.fakeStepBatches1
import com.zhiro.jetstep.domain.test.model.fakeStepBatches2
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class FakeStepRepositoryImpl : StepRepository {

    override suspend fun loadStepBatches(startTime: Long, endTime: Long): Flow<List<StepBatch>> {
        return flow {
            emit(fakeStepBatches1)
            delay(7_000)
            emit(fakeStepBatches2)
        }
    }

    override suspend fun saveStepBatches(stepBatches: List<StepBatch>) {

    }
}