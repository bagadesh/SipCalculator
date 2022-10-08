package com.bagadesh.sipcalculator.ui.base

import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.sp

/**
 * Created by bagadesh on 17/08/22.
 */
@Composable
fun Heading(
    title: String,
    modifier: Modifier = Modifier
) {
    Text(
        text = title,
        fontSize = 22.sp,
        modifier = modifier
    )
}