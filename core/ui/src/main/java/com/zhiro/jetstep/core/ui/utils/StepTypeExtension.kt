package com.zhiro.jetstep.core.ui.utils

import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import com.zhiro.jetstep.core.design.system.theme.Jogging
import com.zhiro.jetstep.core.design.system.theme.Running
import com.zhiro.jetstep.core.design.system.theme.Walking
import com.zhiro.jetstep.core.ui.R
import com.zhiro.jetstep.domain.model.StepType

val StepType.color: Color
    get() = when (this) {
        StepType.Walking -> Color.Walking
        StepType.Jogging -> Color.Jogging
        StepType.Running -> Color.Running
    }

val StepType.icon: Painter
    @Composable
    get() = when (this) {
        StepType.Walking -> painterResource(R.drawable.ic_walking)
        StepType.Jogging -> painterResource(R.drawable.ic_jogging)
        StepType.Running -> painterResource(R.drawable.ic_running)
    }
val StepType.text: String
    @Composable
    get() = when (this) {
        StepType.Walking -> stringResource(R.string.walking)
        StepType.Jogging -> stringResource(R.string.jogging)
        StepType.Running -> stringResource(R.string.running)
    }
