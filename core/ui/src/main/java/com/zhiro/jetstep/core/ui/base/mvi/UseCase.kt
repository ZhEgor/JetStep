package com.zhiro.jetstep.core.ui.base.mvi

import com.zhiro.jetstep.core.common.coroutines.AppDispatchers
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import java.lang.reflect.ParameterizedType

abstract class UseCase<Action, State, A : Action>(protected open val dispatchers: AppDispatchers) {
    open val dispatcher: CoroutineDispatcher = dispatchers.io
    private val actionClass by lazy {
        (javaClass.genericSuperclass as ParameterizedType).actualTypeArguments[2]
    }

    abstract suspend operator fun invoke(action: A, state: State): Action

    open fun canHandle(action: Action): Boolean {
        return action!!::class.java == actionClass
    }
}

abstract class SideEffectUseCase<Action, State, A : Action>(
    dispatchers: AppDispatchers
) : UseCase<Action, State, A>(dispatchers) {
    lateinit var stateProvider: () -> State
    abstract val sideEffectFlow: Flow<Action>
}

interface GenericUseCase<T> {
    suspend fun <Action> invoke(
        onSuccess: suspend (T) -> Action,
        onError: suspend (Exception) -> Action
    ): Action
}

interface ErrorFlowComponent {
    val errorFlow: Flow<String>
}
