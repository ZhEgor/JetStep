package com.zhiro.jetstep.feature.dashboard.presentation.dashboard

import com.zhiro.jetstep.core.common.coroutines.ModelCoroutineScope
import com.zhiro.jetstep.core.ui.base.mvi.BaseViewModel
import com.zhiro.jetstep.domain.model.StepBatch
import com.zhiro.jetstep.feature.dashboard.presentation.dashboard.usecase.LoadStepBatchesUseCase
import com.zhiro.jetstep.feature.dashboard.presentation.dashboard.usecase.SaveStepBatchManuallyUseCase
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview

@OptIn(FlowPreview::class, ExperimentalCoroutinesApi::class)
internal class DashboardViewModel(
    coroutineScope: ModelCoroutineScope,
    loadStepBatchesUseCase: LoadStepBatchesUseCase,
    saveStepBatchManuallyUseCase: SaveStepBatchManuallyUseCase
) : BaseViewModel<DashboardAction, DashboardState, DashboardUiState>(
    reducer = DashboardReducer(),
    stateMapper = DashboardUiState.stateMapper(),
    coroutineScope = coroutineScope,
    initialAction = DashboardAction.None,
    useCase = setOf(
        loadStepBatchesUseCase,
        saveStepBatchManuallyUseCase
    )
) {

    init {
        startLoading()
    }

    private fun startLoading() {
        action(DashboardAction.Load(isLoading = true))
    }

    fun saveManually(stepBatch: StepBatch) {
        action(DashboardAction.SaveStepsManually(stepBatch = stepBatch))
    }
}
