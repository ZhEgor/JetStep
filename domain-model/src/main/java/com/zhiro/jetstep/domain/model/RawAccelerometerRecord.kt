package com.zhiro.jetstep.domain.model

import com.zhiro.jetstep.datasource.room.models.AccelerometerEntity

class RawAccelerometerRecord(
    val x: Float,
    val y: Float,
    val z: Float,
    val recordTakenAt: Long,
    val pace: String,
)

fun RawAccelerometerRecord.toEntity(): AccelerometerEntity {
    return AccelerometerEntity(
        x = x,
        y = y,
        z = z,
        timestamp = recordTakenAt,
        pace = pace
    )
}

fun AccelerometerEntity.toDomain(): RawAccelerometerRecord {
    return RawAccelerometerRecord(
        x = x,
        y = y,
        z = z,
        recordTakenAt = timestamp,
        pace = pace
    )
}
