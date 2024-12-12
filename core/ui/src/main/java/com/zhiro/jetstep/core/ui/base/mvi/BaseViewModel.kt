package com.zhiro.jetstep.core.ui.base.mvi

import androidx.lifecycle.ViewModel
import com.zhiro.jetstep.core.common.coroutines.AppDispatchers
import com.zhiro.jetstep.core.common.coroutines.ModelCoroutineScope
import com.zhiro.jetstep.core.ui.base.mvi.util.delegate
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.cancel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.flattenMerge
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

@FlowPreview
@ExperimentalCoroutinesApi
abstract class BaseViewModel<Action : MviAction<State>, State : Any, UiState : Any>(
    private val reducer: Reducer<Action, State>,
    private val stateMapper: StateMapper<State, UiState>,
    private val useCase: Set<UseCase<Action, State, out Action>>,
    private val coroutineScope: ModelCoroutineScope,
    initialAction: Action,
) : ViewModel(), CoroutineScope by coroutineScope {

    private val mutableState = MutableStateFlow(reducer.initialState)
    protected var stateValue: State by mutableState.delegate()
    val state: StateFlow<UiState> = createUiStateFlow()

    private val actionListeners = mutableListOf<ViewModelActionHandler<Action>>()
    protected val dispatchers: AppDispatchers
        get() = coroutineScope.dispatchers

    init {
        launch(dispatchers.main) {
            flowOf(
                *useCase.filterIsInstance<SideEffectUseCase<Action, State, *>>()
                    .onEach {
                        it.stateProvider = { stateValue }
                    }.map {
                        it.sideEffectFlow
                    }.toTypedArray()
            ).flattenMerge()
                .collect {
                    action(it)
                }
        }
        action(initialAction)
    }

    protected fun action(action: Action) {
        stateValue = reducer.reduce(action, stateValue)
        notifyListeners(action)
        filterUseCase(action).forEach {
            launch(it.dispatcher) {
                val result = it.invoke(action, stateValue)
                withContext(dispatchers.main) {
                    action(result)
                }
            }
        }
    }

    @Suppress("UNCHECKED_CAST")
    private fun <T : Action> filterUseCase(action: T): List<UseCase<Action, State, T>> {
        return useCase.mapNotNull {
            it as? UseCase<Action, State, T>
        }.filter {
            it.canHandle(action)
        }
    }

    private fun notifyListeners(action: Action) {
        actionListeners.filter {
            it.filter(action)
        }.forEach {
            it.onAction(action)
        }
    }

    protected fun handleAction(filter: (Action) -> Boolean, onAction: (Action) -> Unit) {
        actionListeners.add(ViewModelActionHandler(filter, onAction))
    }

    private fun createUiStateFlow(): StateFlow<UiState> {
        val uiStateFlow = MutableStateFlow(stateMapper.toUi(reducer.initialState))
        launch(dispatchers.main) {
            mutableState.collectLatest {
                uiStateFlow.value = stateMapper.toUi(it)
            }
        }
        return uiStateFlow
    }

    override fun onCleared() {
        cancel()
        super.onCleared()
    }
}
