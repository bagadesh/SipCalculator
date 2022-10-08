package com.bagadesh.sipcalculator.home.ui.sip

import androidx.compose.foundation.layout.*
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.bagadesh.domain.constants.InvestmentDetailConstants
import com.bagadesh.domain.constants.SipInvestmentDetailsConstants
import com.bagadesh.domain.entities.investment.InvestmentType
import com.bagadesh.sipcalculator.entities.SaveResultsCurrentData
import com.bagadesh.sipcalculator.home.ui.DefaultRateOfInterest
import com.bagadesh.sipcalculator.home.ui.DefaultRateOfInterestMax
import com.bagadesh.sipcalculator.home.ui.amount.PrincipalUI
import com.bagadesh.sipcalculator.home.ui.components.CalculateButton
import com.bagadesh.sipcalculator.home.ui.interest.RateOfInterestUI
import com.bagadesh.sipcalculator.home.ui.result.*
import com.bagadesh.sipcalculator.home.ui.tenure.TenureUI
import com.bagadesh.sipcalculator.ui.base.Heading
import com.bagadesh.sipcalculator.ui.base.SizeSpacer
import com.bagadesh.sipcalculator.ui.base.UIStatePark

/**
 * Created by bagadesh on 21/08/22.
 */
@Composable
fun SipUI(
    viewModel: SipViewModel = hiltViewModel(),
    onSaveClick: (SaveResultsCurrentData) -> Unit
) {
    var year by viewModel.year
    var sipAmount by viewModel.principal
    var interest by viewModel.interest
    val investmentType = InvestmentType.SIP
    val result by viewModel.investmentResultData.collectAsState()
    val inflationAdjustedResult by viewModel.inflationAdjustedResultData.collectAsState()

    Column {
        Heading(title = "Calculate your earnings")
        10.dp.SizeSpacer()
        PrincipalUI(principal = sipAmount) { principal -> sipAmount = principal }
        30.dp.SizeSpacer()
        RateOfInterestUI(
            defaultInterest = DefaultRateOfInterest,
            maxInterest = DefaultRateOfInterestMax,
            onValueChange = { changedInterest ->
                interest = changedInterest
            }
        )
        30.dp.SizeSpacer()
        TenureUI(value = year, onValueChange = { changedValue -> year = changedValue })
        CalculateButton { viewModel.calculate() }

        //Results
        50.dp.SizeSpacer()
        UIStatePark(state = result) {
            DisplaySipResultUI(resultData = it)
            UIStatePark(state = inflationAdjustedResult) { inflationAdjustedData ->
                ShowDownArrowUI()
                DisplaySipResultUI(resultData = inflationAdjustedData, title = "Inflation Adjusted Return")
            }
            ClearResultButton { viewModel.clearResults() }
            SaveResultButton {
                onSaveClick(
                    SaveResultsCurrentData.Success(
                        investmentType,
                        mapOf(
                            SipInvestmentDetailsConstants.SIP_AMOUNT to sipAmount,
                            SipInvestmentDetailsConstants.RATE_OF_RETURN to interest,
                            SipInvestmentDetailsConstants.TENURE to year,
                            SipInvestmentDetailsConstants.TOTAL to it.result,
                            SipInvestmentDetailsConstants.INTEREST to it.interestEarned,
                            SipInvestmentDetailsConstants.TOTAL_INVESTED to it.totalInvested,
                            SipInvestmentDetailsConstants.INTEREST_PERCENTAGE_GAINED to it.interestPercentageGained,
                        )
                    )
                )
            }
        }
        200.dp.SizeSpacer()
    }
}
