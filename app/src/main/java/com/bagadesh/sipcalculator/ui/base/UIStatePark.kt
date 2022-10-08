package com.bagadesh.sipcalculator.ui.base

import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.layout.Column
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.bagadesh.sipcalculator.entities.states.UIState

/**
 * Created by bagadesh on 17/08/22.
 */
@Composable
fun <T> UIStatePark(
    state: UIState<T>,
    empty: @Composable (() -> Unit)? = null,
    failure: @Composable (() -> Unit)? = null,
    success: @Composable (T) -> Unit,
) {
    Column(modifier = Modifier.animateContentSize()) {
        when (state) {
            is UIState.Empty -> {
                empty?.invoke()
            }
            is UIState.Failure -> {
                failure?.invoke() ?: Text(text = state.message)
            }
            is UIState.Success -> {
                success(state.data)
            }
        }
    }
}