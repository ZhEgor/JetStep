package com.zhiro.jetstep.core.ui.component.spacer

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.Dp

@Composable
fun SimpleSpacer(space: Dp) {
    Spacer(modifier = Modifier.size(size = space))
}

@Composable
fun SystemBarSpacer() {
    Spacer(
        modifier = Modifier
            .statusBarsPadding()
            .background(Color.Green)
    )
}

@Composable
fun NavigationBarSpacer() {
    Spacer(
        modifier = Modifier
            .navigationBarsPadding()
            .background(Color.Green)
    )
}
