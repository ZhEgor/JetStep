package com.zhiro.jetstep.datasource.room.models

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.zhiro.jetstep.datasource.room.models.StepBatchEntity.Companion.TABLE_NAME

@Entity(tableName = TABLE_NAME)
data class StepBatchEntity(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = COLUMN_ID) val id: Int? = null,
    @ColumnInfo(name = COLUMN_STEPS) val steps: Int,
    @ColumnInfo(name = COLUMN_TYPE_ID) val typeId: Int,
    @ColumnInfo(name = COLUMN_START_TIME) val startTime: Long,
    @ColumnInfo(name = COLUMN_END_TIME) val endTime: Long,
) {

    internal companion object {

        const val TABLE_NAME = "step_batch_data"

        const val COLUMN_ID = "id"
        const val COLUMN_STEPS = "steps"
        const val COLUMN_START_TIME = "start_time"
        const val COLUMN_END_TIME = "end_time"
        const val COLUMN_TYPE_ID = "type_id"
    }
}
