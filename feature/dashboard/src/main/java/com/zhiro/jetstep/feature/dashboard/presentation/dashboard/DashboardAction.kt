package com.zhiro.jetstep.feature.dashboard.presentation.dashboard

import com.zhiro.jetstep.core.ui.base.mvi.MviAction
import com.zhiro.jetstep.domain.model.StepBatch

sealed class DashboardAction : MviAction<DashboardState> {

    data object None : DashboardAction()

    data class Load(val isLoading: Boolean) : DashboardAction() {
        override fun accept(state: DashboardState): DashboardState {
            return state.copy(isLoading = isLoading)
        }
    }

    data class SaveStepsManually(val stepBatch: StepBatch) : DashboardAction() {
        override fun accept(state: DashboardState): DashboardState {
            return state.copy(isLoading = true)
        }
    }

    data class Loaded(val stepBatches: List<StepBatch>) : DashboardAction() {
        override fun accept(state: DashboardState): DashboardState {
            return state.copy(
                steps = stepBatches,
                isLoading = false
            )
        }
    }
}
