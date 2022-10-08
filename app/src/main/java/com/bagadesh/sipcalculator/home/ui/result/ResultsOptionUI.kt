package com.bagadesh.sipcalculator.home.ui.result

import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color

/**
 * Created by bagadesh on 27/08/22.
 */
@Composable
fun ColumnScope.CustomButton(
    modifier: Modifier = Modifier,
    text: String,
    onClick: () -> Unit
) {
    Button(
        onClick = {
            onClick()
        },
        modifier = modifier.then(
            Modifier.align(Alignment.CenterHorizontally)
        )
    ) {
        Text(text = text, color = Color.White)
    }
}

@Composable
fun ColumnScope.ClearResultButton(
    modifier: Modifier = Modifier,
    onClick: () -> Unit
) {
    CustomButton(modifier = modifier, text = "Clear results", onClick = onClick)
}

@Composable
fun ColumnScope.SaveResultButton(
    modifier: Modifier = Modifier,
    onClick: () -> Unit
) {
    CustomButton(modifier = modifier, text = "Save results", onClick = onClick)
}

