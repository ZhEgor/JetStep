package com.zhiro.jetstep.feature.dashboard.presentation.dashboard

import com.zhiro.jetstep.core.ui.base.mvi.Reducer

internal class DashboardReducer : Reducer<DashboardAction, DashboardState> {

    override val initialState = DashboardState(
        isLoading = false,
        steps = listOf()
    )
}
