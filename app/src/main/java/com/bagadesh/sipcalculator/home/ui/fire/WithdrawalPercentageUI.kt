package com.bagadesh.sipcalculator.home.ui.fire

import androidx.compose.foundation.layout.padding
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.bagadesh.domain.misc.fire.provideWithdrawalPercentages
import com.bagadesh.sipcalculator.ui.custom.selection.DisplayCustomSelection
import com.bagadesh.sipcalculator.ui.custom.selection.toSelectionItems

/**
 * Created by bagadesh on 30/08/22.
 */
@Composable
fun WithdrawalPercentageUI(
    withdrawalPercentage: Int,
    onValueChange: (Int) -> Unit
) {
    val modifiedWithdrawalPercentageList = remember {
        provideWithdrawalPercentages.toSelectionItems { "$it%" }
    }
    Text(
        text = "Withdrawal percentage",
        modifier = Modifier.padding(top = 10.dp, start = 5.dp),
        fontWeight = FontWeight.SemiBold
    )
    modifiedWithdrawalPercentageList.DisplayCustomSelection(
        selectedItem = withdrawalPercentage,
        onValueChange = onValueChange
    )
}