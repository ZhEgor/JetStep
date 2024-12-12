package com.zhiro.jetstep.core.navigation.domain

import androidx.activity.ComponentActivity
import androidx.navigation.NavHostController
import androidx.navigation.NavOptionsBuilder

interface NavigationManager {

    fun <T : Any> navigateTo(route: T, builder: NavOptionsBuilder.() -> Unit = {})

    fun popBackStack()

    /**
     * Should be called on Activity.onCreate()
     */
    fun attach(activity: ComponentActivity, navController: NavHostController)

    /**
     * Should be called on Activity.onDestroy()
     */
    fun detach()
}

class NavigationManagerImpl : NavigationManager {

    private var navController: NavHostController? = null
    private var activity: ComponentActivity? = null

    override fun <T : Any> navigateTo(route: T, builder: NavOptionsBuilder.() -> Unit) {
        navController?.navigate(route = route, builder = builder)
    }

    override fun popBackStack() {
        if (navController?.navigateUp() != true) {
            activity?.onBackPressedDispatcher?.onBackPressed()
        }
    }

    /**
     * Should be called on Activity.onCreate()
     */
    override fun attach(activity: ComponentActivity, navController: NavHostController) {
        this.activity = activity
        this.navController = navController
    }

    /**
     * Should be called on Activity.onDestroy()
     */
    override fun detach() {
        this.navController = null
        this.activity = null
    }
}
