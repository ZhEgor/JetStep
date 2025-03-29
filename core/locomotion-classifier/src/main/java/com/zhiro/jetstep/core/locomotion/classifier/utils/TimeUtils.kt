package com.zhiro.jetstep.core.locomotion.classifier.utils

internal inline fun <R> printDuration(tag: String = "TAG!!!", block: () -> R): R {
    val startTime = System.currentTimeMillis()
    val blockResult = block.invoke()
    val endTime = System.currentTimeMillis()
    println("$tag duration: ${endTime - startTime}")
    return blockResult
}
