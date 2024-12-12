package com.zhiro.jetstep.core.ui.base.mvi.util

import com.zhiro.jetstep.core.ui.base.mvi.BaseViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.MutableStateFlow
import kotlin.properties.ReadWriteProperty
import kotlin.reflect.KProperty

@OptIn(FlowPreview::class, ExperimentalCoroutinesApi::class)
fun <T> MutableStateFlow<T>.delegate() = object : ReadWriteProperty<BaseViewModel<*, *, *>, T> {

    override fun setValue(
        thisRef: BaseViewModel<*, *, *>,
        property: KProperty<*>,
        value: T
    ) {
        this@delegate.value = value
    }

    override fun getValue(thisRef: BaseViewModel<*, *, *>, property: KProperty<*>): T {
        return requireNotNull(this@delegate.value)
    }
}
