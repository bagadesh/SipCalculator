package com.bagadesh.sipcalculator.ui.theme

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.bagadesh.sipcalculator.debug.SHOW_COLOR_BAR

/**
 * Created by bagadesh on 02/07/22.
 */

private val borderSize = RoundedCornerShape(10.dp)

@Composable
fun MaterialColorUI() {
    if (SHOW_COLOR_BAR) {
        val listOfColors = listOf(
            "primary" to MaterialTheme.colors.primary,
            "onPrimary" to MaterialTheme.colors.onPrimary,
            "background" to MaterialTheme.colors.background,
            "onBackground" to MaterialTheme.colors.onBackground,
            "secondary" to MaterialTheme.colors.secondary,
            "onSecondary" to MaterialTheme.colors.onSecondary,
            "surface" to MaterialTheme.colors.surface,
            "onSurface" to MaterialTheme.colors.onSurface,
            "primaryVariant" to MaterialTheme.colors.primaryVariant,
            "secondaryVariant" to MaterialTheme.colors.secondaryVariant,
        )
        LazyRow {
            items(listOfColors) {
                Box(
                    modifier = Modifier
                        .padding(horizontal = 1.dp)
                        .wrapContentSize()
                        .border(1.dp, MaterialTheme.colors.onSurface, shape = borderSize)
                        .clip(borderSize)
                        .background(it.second)
                        .padding(15.dp)
                ) {
                    ColorText(it)
                }
            }
        }
        Spacer(modifier = Modifier.size(40.dp))
    }
}

val textColors = listOf(Color.White, Color.Black)

@Composable
fun ColorText(currentPair: Pair<String, Color>) {
    Text(
        text = currentPair.first,
        fontSize = 12.sp,
        color = if (textColors.first() == currentPair.second) {
            textColors.last()
        } else {
            textColors.first()
        }
    )
}