package com.zhiro.jetstep.datasource.room.dao

import androidx.room.Dao
import androidx.room.Query
import com.zhiro.jetstep.datasource.room.base.BaseDao
import com.zhiro.jetstep.datasource.room.models.AccelerometerEntity
import com.zhiro.jetstep.datasource.room.models.StepBatchEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface StepBatchDao : BaseDao<StepBatchEntity> {

    @Query("SELECT * FROM ${StepBatchEntity.TABLE_NAME}")
    fun readAll(): Flow<List<StepBatchEntity>>

    @Query("SELECT * FROM ${StepBatchEntity.TABLE_NAME} WHERE ${StepBatchEntity.COLUMN_START_TIME} BETWEEN :startTime AND :endTime")
    fun readInterval(startTime: Long, endTime: Long): Flow<List<StepBatchEntity>>

    @Query("DELETE FROM ${AccelerometerEntity.TABLE_NAME}")
    fun dropTable()
}
