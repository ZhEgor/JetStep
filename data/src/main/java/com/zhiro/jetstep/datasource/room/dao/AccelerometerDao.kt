package com.zhiro.jetstep.datasource.room.dao

import androidx.room.Dao
import androidx.room.Query
import com.zhiro.jetstep.datasource.room.base.BaseDao
import com.zhiro.jetstep.datasource.room.models.AccelerometerEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface AccelerometerDao : BaseDao<AccelerometerEntity> {

    @Query("SELECT * FROM ${AccelerometerEntity.TABLE_NAME}")
    fun readAll(): Flow<List<AccelerometerEntity>>

    @Query("DELETE FROM ${AccelerometerEntity.TABLE_NAME}")
    fun dropTable()
}
