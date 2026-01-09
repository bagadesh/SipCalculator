package com.bagadesh.sipcalculator.home.ui.oneTimeThenSip

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.bagadesh.domain.entities.OneTimeThenSipResultData
import com.bagadesh.domain.entities.investment.InvestmentType
import com.bagadesh.sipcalculator.entities.SaveResultsCurrentData
import com.bagadesh.sipcalculator.home.ui.DefaultRateOfInterestMax
import com.bagadesh.sipcalculator.home.ui.components.CalculateButton
import com.bagadesh.sipcalculator.home.ui.compoundFrequency.CompoundFrequencyUI
import com.bagadesh.sipcalculator.home.ui.interest.RateOfInterestUI
import com.bagadesh.sipcalculator.home.ui.result.*
import com.bagadesh.sipcalculator.home.ui.tenure.TenureUI
import com.bagadesh.sipcalculator.ui.base.Heading
import com.bagadesh.sipcalculator.ui.base.SizeSpacer
import com.bagadesh.sipcalculator.ui.base.UIStatePark
import com.bagadesh.sipcalculator.ui.base.money.MoneyRequestUI

/**
 * Created by bagadesh.
 */
@Composable
fun OneTimeThenSipUI(
    viewModel: OneTimeThenSipViewModel = hiltViewModel(),
    onSaveClick: (SaveResultsCurrentData) -> Unit
) {
    var oneTimeAmount by viewModel.oneTimeAmount
    var sipAmount by viewModel.sipAmount
    var interest by viewModel.interest
    var year by viewModel.year
    var compoundFrequency by viewModel.compoundFrequency

    val result by viewModel.investmentResultData.collectAsState()
    val inflationResult by viewModel.inflationAdjustedResultData.collectAsState()
    val investmentType = InvestmentType.ONE_TIME_THEN_SIP

    Column {
        Heading(title = "Calculate your earnings")
        10.dp.SizeSpacer()

        MoneyRequestUI(title = "One Time Investment", money = oneTimeAmount) { oneTimeAmount = it }
        10.dp.SizeSpacer()
        MoneyRequestUI(title = "SIP Investment", money = sipAmount) { sipAmount = it }

        30.dp.SizeSpacer()
        RateOfInterestUI(
            defaultInterest = interest,
            maxInterest = DefaultRateOfInterestMax
        ) { interest = it }

        30.dp.SizeSpacer()
        TenureUI(value = year) { year = it }

        CompoundFrequencyUI(compoundFrequency = compoundFrequency) { compoundFrequency = it }

        CalculateButton { viewModel.calculate() }

        50.dp.SizeSpacer()

        UIStatePark(state = result) { data ->
            DisplayOneTimeThenSipResult(data)

            UIStatePark(state = inflationResult) { infData ->
                ShowDownArrowUI()
                DisplayOneTimeThenSipResult(infData, title = "Inflation Adjusted Return")
            }

            ClearResultButton { viewModel.clearResults() }
            
            // Save logic can be added here if keys are defined
        }
        200.dp.SizeSpacer()
    }
}

@Composable
fun DisplayOneTimeThenSipResult(
    data: OneTimeThenSipResultData,
    title: String? = null
) {
    Column(modifier = Modifier.padding(10.dp)) {
        if (title != null) {
            Text(
                text = title,
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(bottom = 10.dp)
            )
        }
        
        Text(text = "One Time Result", fontWeight = FontWeight.SemiBold)
        DisplayOneTimeResultUI(resultData = data.oneTimeResultData, title = " ")
        
        10.dp.SizeSpacer()
        Text(text = "SIP Result", fontWeight = FontWeight.SemiBold)
        DisplaySipResultUI(resultData = data.sipResultData, title = " ")

        10.dp.SizeSpacer()
        Text(text = "Total Combined Result", fontWeight = FontWeight.SemiBold)
        HorizontalResultUI(
            list = listOf(
                HorizontalResultData(
                    title = "Total",
                    value = data.totalResult,
                    color = Color.Magenta
                )
            )
        )
    }
}

