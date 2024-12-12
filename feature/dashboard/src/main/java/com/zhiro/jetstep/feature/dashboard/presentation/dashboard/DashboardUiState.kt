package com.zhiro.jetstep.feature.dashboard.presentation.dashboard

import com.zhiro.jetstep.core.ui.base.mvi.StateMapper
import com.zhiro.jetstep.domain.model.StepBatch

internal data class DashboardUiState(
    val isLoading: Boolean,
    val steps: List<StepBatch>
) {

    companion object {

        fun stateMapper(): StateMapper<DashboardState, DashboardUiState> {
            return StateMapper { state ->
                DashboardUiState(
                    isLoading = state.isLoading,
                    steps = state.steps,
                )
            }
        }
    }
}
