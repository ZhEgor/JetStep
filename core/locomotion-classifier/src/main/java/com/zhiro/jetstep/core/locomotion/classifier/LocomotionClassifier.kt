package com.zhiro.jetstep.core.locomotion.classifier

import ai.onnxruntime.OnnxTensor
import ai.onnxruntime.OrtEnvironment
import ai.onnxruntime.OrtSession
import android.content.Context
import com.zhiro.jetstep.core.locomotion.classifier.model.ClassifiedSteps
import com.zhiro.jetstep.core.locomotion.classifier.model.step2.StepsDetails
import com.zhiro.jetstep.core.locomotion.classifier.model.step5.toFloatArray
import java.nio.FloatBuffer

internal interface LocomotionClassifier {

    fun predict(stepsDetails: StepsDetails): ClassifiedSteps?
}

internal class LocomotionClassifierImpl(private val applicationContext: Context, ) : LocomotionClassifier {

    override fun predict(stepsDetails: StepsDetails): ClassifiedSteps? {
        if (stepsDetails.stepsAmount < 5) return null
        val inputs = stepsDetails.stepFeatures.toFloatArray()
        if (inputs.isEmpty()) return null

        val ortEnvironment = OrtEnvironment.getEnvironment()
        val ortSessionScaler = createORTSessionScaler(ortEnvironment)
        val outputScaler = runScaler(inputs, ortSessionScaler, ortEnvironment)

        val inputClassifier = outputScaler.toList().map(FloatArray::toList).flatten().toFloatArray()
        val ortSessionClassifier = createORTSessionClassifier(ortEnvironment)
        val output = runPrediction(inputClassifier, ortSessionClassifier, ortEnvironment)
            .toList()
            .groupingBy { it }
            .eachCount()
            .maxByOrNull(Map.Entry<Long, Int>::value)
            ?.key

        return if (output != null) {
            ClassifiedSteps.create(
                stepsDetails = stepsDetails,
                typeId = output
            )
        } else {
            null
        }
    }

    // Make predictions with given inputs
    private fun runPrediction(
        input: FloatArray,
        ortSession: OrtSession,
        ortEnvironment: OrtEnvironment,
    ): LongArray {
        // Get the name of the input node
        val inputName = ortSession.inputNames?.iterator()?.next()
        // Make a FloatBuffer of the inputs
        val floatBufferInputs = FloatBuffer.wrap(input)
        val featureArraysCount = input.size / FEATURES_COUNT

        val inputTensor = OnnxTensor.createTensor(
            ortEnvironment,
            floatBufferInputs,
            longArrayOf(featureArraysCount, FEATURES_COUNT)
        )
        // Run the model
        val results = ortSession.run(mapOf(inputName to inputTensor))
        // Fetch and return the results
        return results[0].value as LongArray
    }

    // Make predictions with given inputs
    private fun runScaler(
        input: FloatArray,
        ortSession: OrtSession,
        ortEnvironment: OrtEnvironment,
    ): Array<FloatArray> {
        // Get the name of the input node
        val inputName = ortSession.inputNames?.iterator()?.next()
        // Make a FloatBuffer of the inputs
        val floatBufferInputs = FloatBuffer.wrap(input)
        val featureArraysCount = input.size / FEATURES_COUNT

        val inputTensor = OnnxTensor.createTensor(
            ortEnvironment,
            floatBufferInputs,
            longArrayOf(featureArraysCount, FEATURES_COUNT)
        )
        // Run the model
        val results = ortSession.run(mapOf(inputName to inputTensor))
        // Fetch and return the results
        return results[0].value as Array<FloatArray>
    }

    // Create an OrtSession
    private fun createORTSessionClassifier(ortEnvironment: OrtEnvironment): OrtSession {
        val modelBytes =
            applicationContext.resources.openRawResource(R.raw.sklearn_model).readBytes()
        return ortEnvironment.createSession(modelBytes)
    }

    private fun createORTSessionScaler(ortEnvironment: OrtEnvironment): OrtSession {
        val modelBytes =
            applicationContext.resources.openRawResource(R.raw.sklearn_scaler).readBytes()
        return ortEnvironment.createSession(modelBytes)
    }

    companion object {
        const val FEATURES_COUNT = 3L
    }
}

