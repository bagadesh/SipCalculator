package com.bagadesh.sipcalculator.home.ui.bond

import androidx.compose.foundation.layout.Column
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.BlurredEdgeTreatment
import androidx.compose.ui.draw.blur
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.bagadesh.domain.entities.BondResultData
import com.bagadesh.sipcalculator.home.ui.result.HorizontalResultData
import com.bagadesh.sipcalculator.home.ui.result.HorizontalResultUI
import com.bagadesh.sipcalculator.home.ui.result.ResultTitle
import com.bagadesh.sipcalculator.ui.custom.BlurController

/**
 * Created by bagadesh on 02/08/22.
 */
@Composable
fun BondResultUI(
    bondResultData: BondResultData
) {
//    BlurController(
//        radius = 16.dp,
//        edgeTreatment = BlurredEdgeTreatment.Unbounded
//    ) {
    Column(
        modifier = Modifier
    ) {
        val title = "Bond Result"
        ResultTitle(
            title = title,
            titleColor = MaterialTheme.colors.primary
        )
        HorizontalResultUI(
            list = listOf(
                HorizontalResultData(
                    title = "Invested",
                    value = bondResultData.amount.value,
                    color = Color.Blue
                ),
                HorizontalResultData(
                    title = "Pre Tax (M)",
                    value = bondResultData.monthlyPreTaxInterest.value,
                    color = Color.Yellow
                ),
                HorizontalResultData(
                    title = "Post Tax (M)",
                    value = bondResultData.monthlyPostTaxInterest.value,
                    color = Color.Green
                ),
            ),
            modifier = Modifier
        )
    }
//    }


}