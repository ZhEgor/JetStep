package com.zhiro.jetstep.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import com.zhiro.jetstep.core.navigation.routes.ScreenRoute
import com.zhiro.jetstep.feature.dashboard.presentation.dashboard.dashboardGraph

@Composable
fun NavigationGraph(
    modifier: Modifier = Modifier,
    navController: NavHostController,
    startDestination: ScreenRoute = ScreenRoute.Dashboard,
) {
    NavHost(
        modifier = modifier,
        navController = navController,
        startDestination = startDestination
    ) {
        dashboardGraph()
    }
}
