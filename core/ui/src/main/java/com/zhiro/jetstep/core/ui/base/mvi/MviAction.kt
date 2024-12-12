package com.zhiro.jetstep.core.ui.base.mvi

interface MviAction<State> {

    fun accept(state: State): State {
        return state
    }
}
