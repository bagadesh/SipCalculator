package com.bagadesh.sipcalculator.ui.custom

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

/**
 * Created by bagadesh on 25/07/22.
 */
@Composable
fun Circle(
    modifier: Modifier = Modifier,
    size: Dp,
    color: Color = MaterialTheme.colors.primary
) {
    Box(
        modifier = modifier.then(
            Modifier
                .size(size)
                .background(color = color, RoundedCornerShape(50))
                .clip(RoundedCornerShape(50))
        ),
    )
}