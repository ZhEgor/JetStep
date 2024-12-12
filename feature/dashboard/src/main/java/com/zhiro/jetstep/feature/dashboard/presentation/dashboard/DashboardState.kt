package com.zhiro.jetstep.feature.dashboard.presentation.dashboard

import com.zhiro.jetstep.domain.model.StepBatch

data class DashboardState(
    val isLoading: Boolean,
    val steps: List<StepBatch>
)
