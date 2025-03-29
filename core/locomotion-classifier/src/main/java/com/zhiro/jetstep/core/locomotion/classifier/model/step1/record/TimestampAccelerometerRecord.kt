package com.zhiro.jetstep.core.locomotion.classifier.model.step1.record

internal data class TimestampAccelerometerRecord(
    val x: Float,
    val y: Float,
    val z: Float,
    val collectedAtMillis: Long,
) {
    companion object {

        fun create(x: Float, y: Float, z: Float) = TimestampAccelerometerRecord(
            x = x,
            y = y,
            z = z,
            collectedAtMillis = System.currentTimeMillis()
        )
        fun create(x: Float, y: Float, z: Float,  collectedAtMillis: Long = 0, pace: String) = TimestampAccelerometerRecord(
            x = x,
            y = y,
            z = z,
            collectedAtMillis = collectedAtMillis
        )
    }
}
