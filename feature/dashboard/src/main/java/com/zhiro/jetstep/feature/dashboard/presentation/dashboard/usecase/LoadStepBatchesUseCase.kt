package com.zhiro.jetstep.feature.dashboard.presentation.dashboard.usecase

import com.zhiro.jetstep.core.common.coroutines.AppDispatchers
import com.zhiro.jetstep.core.common.utils.atEndOfDay
import com.zhiro.jetstep.core.ui.base.mvi.SideEffectUseCase
import com.zhiro.jetstep.domain.repository.StepRepository
import com.zhiro.jetstep.feature.dashboard.presentation.dashboard.DashboardAction
import com.zhiro.jetstep.feature.dashboard.presentation.dashboard.DashboardState
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import java.time.LocalDate
import java.time.ZoneId

class LoadStepBatchesUseCase(
    dispatchers: AppDispatchers,
    stepRepository: StepRepository,
) : SideEffectUseCase<DashboardAction, DashboardState, DashboardAction.Load>(
    dispatchers = dispatchers
) {

    private val localDate = LocalDate.now()
    private val startOfDay =
        localDate.atStartOfDay().atZone(ZoneId.systemDefault()).toInstant().toEpochMilli()
    private val endOfDay =
        localDate.atEndOfDay().atZone(ZoneId.systemDefault()).toInstant().toEpochMilli()

    override val sideEffectFlow: Flow<DashboardAction> = stepRepository.loadStepBatches(
        startTime = startOfDay,
        endTime = endOfDay
    ).map(DashboardAction::Loaded)

    override suspend fun invoke(
        action: DashboardAction.Load,
        state: DashboardState,
    ): DashboardAction {
        return DashboardAction.None
    }

    override fun canHandle(action: DashboardAction): Boolean {
        return action is DashboardAction.Load && action.isLoading
    }
}
