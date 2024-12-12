package com.zhiro.jetstep.domain.model

enum class StepType(val id: Int) {
    Walking(id = 0),
    Jogging(id = 1),
    Running(id = 2);

    companion object {

        fun getById(id: Int?) = entries.find { type -> type.id == id }
    }
}

fun StepType.next(): StepType {
    val nextIndex = if (ordinal + 1 == StepType.entries.size) {
        0
    } else {
        ordinal + 1
    }
    return StepType.entries[nextIndex]
}
