package com.zhiro.jetstep.feature.dashboard.presentation.dashboard.usecase

import com.zhiro.jetstep.core.common.coroutines.AppDispatchers
import com.zhiro.jetstep.core.ui.base.mvi.UseCase
import com.zhiro.jetstep.domain.repository.StepRepository
import com.zhiro.jetstep.feature.dashboard.presentation.dashboard.DashboardAction
import com.zhiro.jetstep.feature.dashboard.presentation.dashboard.DashboardState

class SaveStepBatchManuallyUseCase(
    dispatchers: AppDispatchers,
    private val stepRepository: StepRepository,
) : UseCase<DashboardAction, DashboardState, DashboardAction.SaveStepsManually>(
    dispatchers = dispatchers
) {
    override suspend fun invoke(
        action: DashboardAction.SaveStepsManually,
        state: DashboardState,
    ): DashboardAction {
        stepRepository.saveStepBatch(stepBatch = action.stepBatch)
        return DashboardAction.None
    }

}