package com.bagadesh.sipcalculator.home.ui.components

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.unit.dp

/**
 * Created by bagadesh on 21/08/22.
 */
@Composable
fun ColumnScope.CalculateButton(
    title: String = "Calculate",
    onClick: () -> Unit,
) {
    val focusManager = LocalFocusManager.current
    Button(
        shape = RoundedCornerShape(15.dp),
        contentPadding = PaddingValues(15.dp),
        modifier = Modifier
            .padding(top = 10.dp)
            .fillMaxWidth()
            .wrapContentHeight()
            .align(Alignment.CenterHorizontally)
            .padding(10.dp),
        onClick = {
            onClick()
            focusManager.clearFocus()
        }) {
        Text(text = title, color = Color.White)
    }
}