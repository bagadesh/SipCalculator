package com.bagadesh.sipcalculator.ui.base

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.Dp

/**
 * Created by bagadesh on 06/09/22.
 */


@Composable
fun Dp.HeightSpacer(modifier: Modifier = Modifier) {
    Spacer(
        modifier = modifier
            .fillMaxWidth()
            .height(this)
    )
}
@Composable
fun Dp.SizeSpacer(modifier: Modifier = Modifier) {
    Spacer(
        modifier = modifier
            .size(this)
    )
}