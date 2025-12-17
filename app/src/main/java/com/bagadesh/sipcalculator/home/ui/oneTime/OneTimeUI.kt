package com.bagadesh.sipcalculator.home.ui.oneTime

import androidx.compose.foundation.layout.Column
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.bagadesh.domain.constants.InvestmentDetailConstants
import com.bagadesh.domain.constants.OneTimeInvestmentDetailsConstants
import com.bagadesh.domain.entities.investment.InvestmentType
import com.bagadesh.sipcalculator.entities.SaveResultsCurrentData
import com.bagadesh.sipcalculator.home.ui.DefaultRateOfInterestMax
import com.bagadesh.sipcalculator.home.ui.amount.PrincipalUI
import com.bagadesh.sipcalculator.home.ui.components.CalculateButton
import com.bagadesh.sipcalculator.home.ui.compoundFrequency.CompoundFrequencyUI
import com.bagadesh.sipcalculator.home.ui.interest.RateOfInterestUI
import com.bagadesh.sipcalculator.home.ui.result.ClearResultButton
import com.bagadesh.sipcalculator.home.ui.result.DisplayOneTimeResultUI
import com.bagadesh.sipcalculator.home.ui.result.SaveResultButton
import com.bagadesh.sipcalculator.home.ui.result.ShowDownArrowUI
import com.bagadesh.sipcalculator.home.ui.tenure.TenureUI
import com.bagadesh.sipcalculator.ui.base.Heading
import com.bagadesh.sipcalculator.ui.base.HeightSpacer
import com.bagadesh.sipcalculator.ui.base.SizeSpacer
import com.bagadesh.sipcalculator.ui.base.UIStatePark

/**
 * Created by bagadesh on 21/08/22.
 */
@Composable
fun OneTimeUI(
    oneTimeViewModel: OneTimeViewModel = hiltViewModel(),
    onSaveClick: (SaveResultsCurrentData) -> Unit
) {
    var year by oneTimeViewModel.year
    var interest by oneTimeViewModel.interest
    var principal by oneTimeViewModel.principal
    var compoundFrequency by oneTimeViewModel.compoundFrequency
    val result by oneTimeViewModel.investmentResultData.collectAsState()
    val inflationAdjustedResult by oneTimeViewModel.inflationAdjustedResultData.collectAsState()

    Column {
        Heading(title = "Calculate your earnings")
        10.dp.SizeSpacer()
        PrincipalUI(principal = principal) { result -> principal = result }
        30.dp.SizeSpacer()
        RateOfInterestUI(
            defaultInterest = interest,
            maxInterest = DefaultRateOfInterestMax,
            onValueChange = { changedInterest ->
                interest = changedInterest
            }
        )
        30.dp.SizeSpacer()
        TenureUI(value = year, onValueChange = { changedValue -> year = changedValue })
        CompoundFrequencyUI(compoundFrequency = compoundFrequency) { compoundFrequency = it }
        CalculateButton { oneTimeViewModel.calculate() }
        50.dp.SizeSpacer()

        // Results
        UIStatePark(state = result) {
            DisplayOneTimeResultUI(resultData = it)
            UIStatePark(state = inflationAdjustedResult) { inflationAdjustedData ->
                ShowDownArrowUI()
                DisplayOneTimeResultUI(
                    resultData = inflationAdjustedData,
                    title = "Inflation Adjusted Return"
                )
            }
            ClearResultButton { oneTimeViewModel.clearResults() }
            SaveResultButton {
                onSaveClick(
                    SaveResultsCurrentData.Success(
                        InvestmentType.ONE_TIME,
                        mapOf(
                            OneTimeInvestmentDetailsConstants.ONETIME_AMOUNT to principal,
                            OneTimeInvestmentDetailsConstants.RATE_OF_RETURN to interest.toString(),
                            OneTimeInvestmentDetailsConstants.TENURE to year,
                            OneTimeInvestmentDetailsConstants.COMPOUNDING_FREQUENCY to compoundFrequency.displayValue,
                            OneTimeInvestmentDetailsConstants.TOTAL to it.result,
                            OneTimeInvestmentDetailsConstants.INTEREST to it.interestEarned,
                        )
                    )
                )
            }
        }
        200.dp.HeightSpacer()
    }
}

