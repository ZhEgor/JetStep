package com.zhiro.jetstep.datasource.room.models

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = AccelerometerEntity.TABLE_NAME)
data class AccelerometerEntity(
    @PrimaryKey
    @ColumnInfo(name = COLUMN_TIMESTAMP) val timestamp: Long,
    @ColumnInfo(name = COLUMN_X) val x: Float,
    @ColumnInfo(name = COLUMN_Y) val z: Float,
    @ColumnInfo(name = COLUMN_Z) val y: Float,
    @ColumnInfo(name = COLUMN_PACE) val pace: String,
) {

    internal companion object {

        const val TABLE_NAME = "accelerometer_data"

        const val COLUMN_X = "x"
        const val COLUMN_Y = "y"
        const val COLUMN_Z = "z"
        const val COLUMN_TIMESTAMP = "timestamp"
        const val COLUMN_PACE = "pace"
    }
}
