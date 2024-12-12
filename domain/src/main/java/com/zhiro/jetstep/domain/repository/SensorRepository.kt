package com.zhiro.jetstep.domain.repository

import com.zhiro.jetstep.datasource.room.dao.AccelerometerDao
import com.zhiro.jetstep.datasource.room.models.AccelerometerEntity
import com.zhiro.jetstep.domain.model.RawAccelerometerRecord
import com.zhiro.jetstep.domain.model.toDomain
import com.zhiro.jetstep.domain.model.toEntity
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.flow.map

interface SensorRepository {

    suspend fun saveRawAccelerometerRecords(records: List<RawAccelerometerRecord>)

    suspend fun getAllRawAccelerometerRecords(): List<RawAccelerometerRecord>

    fun getAllRawAccelerometerRecordsFlow(): Flow<List<RawAccelerometerRecord>>

    fun deleteAllData()
}

class SensorRepositoryImpl(
    private val accelerometerDao: AccelerometerDao,
) : SensorRepository {

    override suspend fun saveRawAccelerometerRecords(records: List<RawAccelerometerRecord>) {
        accelerometerDao.insert(records.map(RawAccelerometerRecord::toEntity))
    }

    override suspend fun getAllRawAccelerometerRecords(): List<RawAccelerometerRecord> {
        return accelerometerDao.readAll().firstOrNull()?.map(AccelerometerEntity::toDomain)
            ?: emptyList()
    }

    override fun getAllRawAccelerometerRecordsFlow(): Flow<List<RawAccelerometerRecord>> {
        return accelerometerDao.readAll().map { accelerometerRecords ->
            accelerometerRecords.map(AccelerometerEntity::toDomain)
        }
    }

    override fun deleteAllData() {
        accelerometerDao.dropTable()
    }
}
