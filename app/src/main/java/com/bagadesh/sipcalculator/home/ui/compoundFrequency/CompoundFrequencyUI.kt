package com.bagadesh.sipcalculator.home.ui.compoundFrequency

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import com.bagadesh.sipcalculator.ui.custom.selection.DisplayCustomSelection
import com.bagadesh.sipcalculator.ui.custom.selection.toSelectionItems

/**
 * Created by bagadesh on 23/07/22.
 */
@Composable
fun CompoundFrequencyUI(
    compoundFrequency: CompoundFrequency,
    onValueChange: (CompoundFrequency) -> Unit
) {
    val modified = remember {
        CompoundFrequency.values().toList().toSelectionItems { it.displayValue }
    }
    modified.DisplayCustomSelection(selectedItem = compoundFrequency, onValueChange = onValueChange)
}
