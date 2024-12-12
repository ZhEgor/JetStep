package com.zhiro.jetstep.datasource.room.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.zhiro.jetstep.datasource.room.dao.AccelerometerDao
import com.zhiro.jetstep.datasource.room.dao.StepBatchDao
import com.zhiro.jetstep.datasource.room.models.AccelerometerEntity
import com.zhiro.jetstep.datasource.room.models.StepBatchEntity

@Database(
    entities = [AccelerometerEntity::class, StepBatchEntity::class],
    version = SensorDatabase.DATABASE_VERSION,
    exportSchema = true,
)
internal abstract class SensorDatabase : RoomDatabase() {

    abstract fun getAccelerometerDao(): AccelerometerDao

    abstract fun getStepBatchDao(): StepBatchDao

    companion object {

        internal const val DATABASE_NAME = "fitness_database"
        internal const val DATABASE_VERSION = 1
    }
}
