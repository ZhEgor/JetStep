package com.zhiro.jetstep.core.ui.localcomposition

import androidx.compose.runtime.Composable
import androidx.compose.runtime.Stable

@Stable
interface MockCompositionLocal<R> {

    @get:Composable
    val current: R
}
