package com.zhiro.jetstep.core.navigation.routes

import kotlinx.serialization.Serializable

sealed interface ScreenRoute {

    @Serializable
    data object Dashboard : ScreenRoute
}
