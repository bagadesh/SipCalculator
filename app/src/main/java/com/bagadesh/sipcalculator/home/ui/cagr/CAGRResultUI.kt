package com.bagadesh.sipcalculator.home.ui.cagr

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.bagadesh.domain.entities.base.Percentage
import com.bagadesh.domain.result.cagr.CAGRResult
import com.bagadesh.sipcalculator.entities.states.UIState
import com.bagadesh.sipcalculator.ui.base.UIStatePark
import com.bagadesh.sipcalculator.ui.custom.box.MyBoxUI
import com.bagadesh.sipcalculator.ui.theme.MaterialColorUI

/**
 * Created by bagadesh on 21/08/22.
 */

@Preview
@Composable
fun CAGRResultUIPreview() {
    MaterialColorUI()
    CAGRResultUI(
        UIState.Success(
            CAGRResult(
                cagr = Percentage(44.5422),
                absolutePercentage = Percentage(144.5422),
            )
        ),
        modifier = Modifier.padding(vertical = 100.dp, horizontal = 20.dp)
    )
}

@Composable
fun CAGRResultUI(state: UIState<CAGRResult>, modifier: Modifier = Modifier) {
    UIStatePark(state = state) {
        Column(
            modifier = modifier.then(
                Modifier
                    .fillMaxWidth()
                    .wrapContentHeight()
            ),
        ) {
            MyBoxUI(
                first = "${it.cagr.percentageForDisplay}%",
                second = "CAGR",
            )
            MyBoxUI(
                first = "${it.absolutePercentage.percentageForDisplay}%",
                second = "Absolute return",
            )
        }
    }
}

