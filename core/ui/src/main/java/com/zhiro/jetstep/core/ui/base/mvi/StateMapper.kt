package com.zhiro.jetstep.core.ui.base.mvi

fun interface StateMapper<State : Any, UiState : Any> {
    fun toUi(state: State): UiState
}