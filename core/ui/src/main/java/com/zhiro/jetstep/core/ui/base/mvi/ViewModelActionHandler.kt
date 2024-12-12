package com.zhiro.jetstep.core.ui.base.mvi

class ViewModelActionHandler<Action>(
    val filter: (Action) -> Boolean,
    val onAction: (Action) -> Unit
)
