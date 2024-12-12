package com.zhiro.jetstep.feature.dashboard.presentation.dashboard

import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.zhiro.jetstep.core.navigation.routes.ScreenRoute

fun NavGraphBuilder.dashboardGraph() {
    composable<ScreenRoute.Dashboard> {
        DashboardScreen()
    }
}
