package com.bagadesh.sipcalculator.home.ui.result

import androidx.compose.foundation.layout.padding
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.bagadesh.sipcalculator.entities.states.UIState

/**
 * Created by bagadesh on 05/08/22.
 */
@Composable
fun <T> FailureUI(state: UIState.Failure<T>) {
    Text(
        text = buildString {
            append("So! you have done what our Quality Engineers couldn't do").appendLine().appendLine()
            append("We'll fix as soon as you press this Ok button").appendLine().appendLine()
            append("Meanwhile Do apply for Quality Engineer in https://mywebsite.com/careers")
        },
        modifier = Modifier.padding(10.dp),
        fontSize = 18.sp,
    )

    Text(text = state.message, fontSize = 14.sp)
}