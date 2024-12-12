package com.zhiro.jetstep.core.navigation.utils

import androidx.activity.ComponentActivity
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.remember
import androidx.navigation.NavHostController
import com.zhiro.jetstep.core.navigation.domain.NavigationManager
import com.zhiro.jetstep.core.ui.localcomposition.LocalActivity
import org.koin.core.context.GlobalContext

private fun attachNavController(navController: NavHostController, activity: ComponentActivity) {
    getNavigationManager().attach(activity = activity, navController = navController)
}

private fun detachNavController() {
    getNavigationManager().detach()
}

@Composable
fun rememberNavigationManager(): NavigationManager {
    return remember {
        getNavigationManager()
    }
}

private fun getNavigationManager(): NavigationManager {
    return GlobalContext.get().get<NavigationManager>()
}

@Composable
fun InitNavigationManager(navController: NavHostController) {
    val activity = LocalActivity.current

    if (activity != null) {
        DisposableEffect(Unit) {
            attachNavController(activity = activity, navController = navController)

            onDispose {
                detachNavController()
            }
        }
    }
}
