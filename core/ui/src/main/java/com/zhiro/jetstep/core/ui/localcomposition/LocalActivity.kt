package com.zhiro.jetstep.core.ui.localcomposition

import android.content.Context
import android.content.ContextWrapper
import androidx.activity.ComponentActivity
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext

val LocalActivity = object : MockCompositionLocal<ComponentActivity?> {

    override val current: ComponentActivity?
        @Composable
        get() = LocalContext.current.activity
}

@Suppress("RecursivePropertyAccessor")
val Context.activity: ComponentActivity?
    get() = when (this) {
        is ComponentActivity -> this
        is ContextWrapper -> baseContext.activity
        else -> null
    }
