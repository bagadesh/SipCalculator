package com.bagadesh.sipcalculator.home.ui.cagr

import androidx.compose.foundation.layout.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.bagadesh.domain.entities.base.Currency
import com.bagadesh.domain.entities.base.Year
import com.bagadesh.sipcalculator.home.ui.DefaultYear
import com.bagadesh.sipcalculator.home.ui.components.CalculateButton
import com.bagadesh.sipcalculator.home.ui.tenure.TenureUI
import com.bagadesh.sipcalculator.ui.base.Heading
import com.bagadesh.sipcalculator.ui.base.HeightSpacer
import com.bagadesh.sipcalculator.ui.base.SizeSpacer
import com.bagadesh.sipcalculator.ui.base.money.MoneyRequestUI

/**
 * Created by bagadesh on 21/08/22.
 */
@Composable
fun CAGRUI(
    viewModel: CAGRViewModel = hiltViewModel()
) {
    var initial by viewModel.initial
    var final by viewModel.final
    var year by viewModel.year
    val result by viewModel.cagrResult.collectAsState()

    Column {
        Heading(title = "Calculate CAGR")
        MoneyRequestUI(
            title = "Initial",
            money = initial,
            onValueChange = {
                initial = it
            }
        )
        MoneyRequestUI(
            title = "Final",
            money = final,
            onValueChange = {
                final = it
            }
        )
        30.dp.SizeSpacer()
        TenureUI(value = year, onValueChange = { changedValue -> year = changedValue })
        CalculateButton { viewModel.calculate() }
        CAGRResultUI(state = result, modifier = Modifier.padding(top = 30.dp))
        200.dp.HeightSpacer()
    }
}
