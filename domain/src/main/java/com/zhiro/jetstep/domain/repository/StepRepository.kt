package com.zhiro.jetstep.domain.repository

import com.zhiro.jetstep.datasource.room.dao.StepBatchDao
import com.zhiro.jetstep.datasource.room.models.StepBatchEntity
import com.zhiro.jetstep.domain.model.StepBatch
import com.zhiro.jetstep.domain.model.toData
import com.zhiro.jetstep.domain.model.toDomain
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

interface StepRepository {

    fun loadStepBatches(startTime: Long, endTime: Long): Flow<List<StepBatch>>

    suspend fun saveStepBatches(stepBatches: List<StepBatch>)

    suspend fun saveStepBatch(stepBatch: StepBatch)
}

class StepRepositoryImpl(private val stepBatchDao: StepBatchDao) : StepRepository {

    override fun loadStepBatches(startTime: Long, endTime: Long): Flow<List<StepBatch>> {
        return stepBatchDao.readInterval(startTime = startTime, endTime = endTime).map { entities ->
            entities.map(StepBatchEntity::toDomain)
        }
    }

    override suspend fun saveStepBatches(stepBatches: List<StepBatch>) {
        stepBatchDao.insert(stepBatches.map(StepBatch::toData))
    }

    override suspend fun saveStepBatch(stepBatch: StepBatch) {
        stepBatchDao.insert(stepBatch.toData())
    }
}
