package com.bagadesh.sipcalculator.extensions

import com.bagadesh.domain.result.Data
import com.bagadesh.domain.usecases.base.BaseFlowUseCase
import com.bagadesh.domain.usecases.base.BaseUseCase
import com.bagadesh.sipcalculator.entities.states.UIState
import com.bagadesh.sipcalculator.ui.exception.convertToUIMessage
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map

/**
 * Created by bagadesh on 05/08/22.
 */

fun <T> UIState<T>.isSuccess() = this is UIState.Success

inline fun <T> UIState<T>.isSuccess(onBlock: (UIState.Success<T>) -> Unit) {
    if (this is UIState.Success) {
        onBlock(this)
    }
}

fun <Param, Result> BaseUseCase<Param, Result>.toFlow(
    param: Param
): Flow<UIState<Result>> = flow {
    emit(toUIResult(param))
}

fun <T, R> Flow<UIState<T>>.mapUIState(transform: (T) -> R): Flow<UIState<R>> {
    return map {
        when (it) {
            is UIState.Empty -> UIState.Empty()
            is UIState.Failure -> UIState.Failure(it.message)
            is UIState.Success -> {
                UIState.Success(
                    transform(it.data)
                )
            }
        }
    }
}


suspend fun <Param, Result> BaseUseCase<Param, Result>.toUIResult(param: Param): UIState<Result> {
    return when (val result = execute(param)) {
        is Data.Failure -> {
            UIState.Failure(result.exception.convertToUIMessage())
        }
        is Data.Success -> {
            UIState.Success(result.data)
        }
    }
}

fun <Param, Result> BaseFlowUseCase<Param, Result>.toUIResult(param: Param): Flow<UIState<Result>> {
    return execute(param).map {
        when (it) {
            is Data.Failure -> {
                UIState.Failure(it.exception.convertToUIMessage())
            }
            is Data.Success -> {
                UIState.Success(it.data)
            }
        }
    }
}