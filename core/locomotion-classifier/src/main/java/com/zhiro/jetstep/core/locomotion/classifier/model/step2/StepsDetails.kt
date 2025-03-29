package com.zhiro.jetstep.core.locomotion.classifier.model.step2

import com.zhiro.jetstep.core.locomotion.classifier.model.step1.record.TimestampAccelerometerRecord
import com.zhiro.jetstep.core.locomotion.classifier.model.step1.record.TimestampStepRecord
import com.zhiro.jetstep.core.locomotion.classifier.model.step4.ValleyAndPeakRelation
import com.zhiro.jetstep.core.locomotion.classifier.model.step5.StepFeature
import com.zhiro.jetstep.core.locomotion.classifier.utils.algorithms.findLocalPeaksAndValleysFilter
import com.zhiro.jetstep.core.locomotion.classifier.utils.algorithms.findPeakSimple
import com.zhiro.jetstep.core.locomotion.classifier.utils.algorithms.findRelatedPeaksAndValleysFilter
import com.zhiro.jetstep.core.locomotion.classifier.utils.algorithms.mapLowPassFilter
import com.zhiro.jetstep.core.locomotion.classifier.utils.algorithms.toFeatures
import com.zhiro.jetstep.core.locomotion.classifier.utils.printDuration
import com.zhiro.jetstep.core.locomotion.classifier.utils.sumOf
import kotlin.math.sqrt

internal data class StepsDetails(
    val stepsAmount: Long,
    val accelerometerRecords: List<TimestampAccelerometerRecord>,
) {

    // Step 1
    private val accelerometerMagnitudeRecords: List<com.zhiro.jetstep.core.locomotion.classifier.model.step2.AccelerometerMagnitude> get() {
            return printDuration {
                val rmags = accelerometerRecords.map { accelerometerRecord ->
                    com.zhiro.jetstep.core.locomotion.classifier.model.step2.AccelerometerMagnitude(
                        collectedAtMillis = accelerometerRecord.collectedAtMillis,
                        magnitude = sqrt(
                            accelerometerRecord.x * accelerometerRecord.x +
                                accelerometerRecord.y * accelerometerRecord.y +
                                accelerometerRecord.z * accelerometerRecord.z
                        )
                    )
                }

                val gravity = rmags.sumOf(com.zhiro.jetstep.core.locomotion.classifier.model.step2.AccelerometerMagnitude::magnitude) / rmags.size
                rmags.map { rmag ->
                    rmag.copy(magnitude = rmag.magnitude - gravity)
                }.mapLowPassFilter(alpha = LOW_PASS_FILTER_ALPHA)
            }
        }

    // Step 2
    private val accelerometerMagnitudePeaksAndValleysRecords: List<com.zhiro.jetstep.core.locomotion.classifier.model.step2.AccelerometerMagnitude> get() {
        return printDuration {
                val accelerometerMagnitudeRecords = accelerometerMagnitudeRecords
                val peaks = accelerometerMagnitudeRecords.findPeakSimple(threshold = UPPER_THRESHOLD)
                val valleys = accelerometerMagnitudeRecords.findPeakSimple(threshold = LOWER_THRESHOLD, reversed = true)
                (peaks + valleys).sortedBy(com.zhiro.jetstep.core.locomotion.classifier.model.step2.AccelerometerMagnitude::collectedAtMillis).also {
                    println("accelerometerMagnitudePeaksAndValleysRecords size: ${it.size}")
                }
            }
        }

    // Step 3
    private val accelerometerMagnitudeLocalPeaksAndValleysRecords: List<com.zhiro.jetstep.core.locomotion.classifier.model.step2.AccelerometerMagnitude> get() {
        return printDuration {
                accelerometerMagnitudePeaksAndValleysRecords.findLocalPeaksAndValleysFilter(
                    intervalDurationWindow = LOCAL_INTERVAL_DURATION_WINDOW
                ).also {
                    println("accelerometerMagnitudeLocalPeaksAndValleysRecords size: ${it.size}")
                }
            }
        }

    // Step 4
    private val valleyPeakRelationRecords: List<ValleyAndPeakRelation> get() {
        return printDuration {
                accelerometerMagnitudeLocalPeaksAndValleysRecords.findRelatedPeaksAndValleysFilter(
                    intervalDurationWindow = STEP_WINDOW_DURATION,
                    valleyPeakDurationWindow = VALLEY_PEAK_DURATION_WINDOW,
                    lowerThreshold = LOWER_THRESHOLD,
                    upperThreshold = UPPER_THRESHOLD
                ).also {
                    println("valleyPeakRelationRecords size: ${it.size}")
                }
            }
        }

    // Step 5
    val stepFeatures: List<StepFeature> get() {
        return printDuration {
                valleyPeakRelationRecords.toFeatures(error = STEP_INTERVAL_ERROR).also {
                    println("stepFeatures size: ${it.size}")
                }
            }
        }



    companion object {

        private const val STEP_WINDOW_DURATION = 500L
        private const val VALLEY_PEAK_DURATION_WINDOW = 500L
        private const val LOCAL_INTERVAL_DURATION_WINDOW = 300L
        private const val STEP_INTERVAL_ERROR = 100L
        private const val UPPER_THRESHOLD = 5f
        private const val LOWER_THRESHOLD = -2f
        private const val LOW_PASS_FILTER_ALPHA = 0.9f

        fun create(
            timestampStepRecords: List<TimestampStepRecord>,
            timestampAccelerometerRecords: List<TimestampAccelerometerRecord>,
        ) = printDuration {
            StepsDetails(
                stepsAmount = run {
                    val last = timestampStepRecords.lastOrNull()
                    val first = timestampStepRecords.firstOrNull()
                    last?.aggregatedStepsCount?.minus(first?.aggregatedStepsCount ?: 0L) ?: 0L
                },
                accelerometerRecords = timestampAccelerometerRecords
            )
        }
    }
}

