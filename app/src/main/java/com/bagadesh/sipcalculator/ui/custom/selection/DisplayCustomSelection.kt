package com.bagadesh.sipcalculator.ui.custom.selection

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

/**
 * Created by bagadesh on 30/08/22.
 */


fun <T> List<T>.toSelectionItems(
    displayLambda: (T) -> String
): List<SelectionItem<T>> {
    return map {
        SelectionItem(
            display = displayLambda(it),
            item = it
        )
    }
}

@Composable
fun <T> List<SelectionItem<T>>.DisplayCustomSelection(
    selectedItem: T,
    onValueChange: (T) -> Unit,
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(10.dp),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        forEach {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                TextButton(
                    onClick = {
                        onValueChange(it.item)
                    }) {
                    Text(text = it.display)
                }
                SelectedItemUI(it.item == selectedItem)
            }
        }
    }
}

@Composable
fun SelectedItemUI(selected: Boolean) {
    Box(
        modifier = Modifier
            .size(height = 3.dp, width = 20.dp)
            .background(color = Color.Transparent)

    ) {
        AnimatedVisibility(visible = selected) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(color = MaterialTheme.colors.primary)
            )
        }
    }
}