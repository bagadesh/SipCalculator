package com.bagadesh.sipcalculator.home.ui.sipThenOneTime

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.bagadesh.domain.constants.InvestmentDetailConstants
import com.bagadesh.domain.constants.SipThenOneTimeInvestmentDetailsConstants
import com.bagadesh.domain.entities.investment.InvestmentType
import com.bagadesh.sipcalculator.entities.SaveResultsCurrentData
import com.bagadesh.sipcalculator.home.ui.DefaultRateOfInterest
import com.bagadesh.sipcalculator.home.ui.DefaultRateOfInterestMax
import com.bagadesh.sipcalculator.home.ui.amount.PrincipalUI
import com.bagadesh.sipcalculator.home.ui.components.CalculateButton
import com.bagadesh.sipcalculator.home.ui.compoundFrequency.CompoundFrequencyUI
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
fun SipThenOneTimeUI(
    viewModel: SipThenOneTimeViewModel = hiltViewModel(),
    onSaveClick: (SaveResultsCurrentData) -> Unit
) {
    var year by viewModel.year
    val result by viewModel.investmentResultData.collectAsState()
    var sipAmount by viewModel.principal
    var interest by viewModel.interest
    var yearForSipThenOneTime by viewModel.oneTimeYear
    val investmentType = InvestmentType.SIP_THEN_ONE_TIME
    var compoundFrequency by viewModel.compoundFrequency
    var interestForSipThenOneTime by viewModel.oneTimeInterest
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
        OneTimeUIDetailsAfterSip {
            Text(
                text = "After SIP",
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.align(Alignment.CenterHorizontally)
            )
            10.dp.SizeSpacer()
            RateOfInterestUI(
                defaultInterest = DefaultRateOfInterest,
                maxInterest = DefaultRateOfInterestMax,
                onValueChange = { changedInterest ->
                    interestForSipThenOneTime = changedInterest
                }
            )
            10.dp.SizeSpacer()
            TenureUI(value = yearForSipThenOneTime, onValueChange = { changedValue -> yearForSipThenOneTime = changedValue })
            CompoundFrequencyUI(compoundFrequency = compoundFrequency) { compoundFrequency = it }
        }
        CalculateButton { viewModel.calculate() }

        //Results
        50.dp.SizeSpacer()
        UIStatePark(state = result) {
            ShowSipResultUI(resultData = it.sipResultData)
            ShowDownArrowUI()
            DisplayOneTimeResultUI(resultData = it.oneTimeResultData)
            UIStatePark(state = inflationAdjustedResult) { inflationAdjustedResult ->
                ShowDownArrowUI()
                DisplayOneTimeResultUI(
                    resultData = inflationAdjustedResult.oneTimeResultData,
                    title = "Inflation Adjusted Return"
                )
            }
            ClearResultButton { viewModel.clearResults() }
            SaveResultButton {
                onSaveClick(
                    SaveResultsCurrentData.Success(
                        investmentType,
                        mapOf(
                            SipThenOneTimeInvestmentDetailsConstants.SIP_AMOUNT to sipAmount,
                            SipThenOneTimeInvestmentDetailsConstants.RATE_OF_RETURN to interest,
                            SipThenOneTimeInvestmentDetailsConstants.TENURE to year,
                            SipThenOneTimeInvestmentDetailsConstants.COMPOUNDING_FREQUENCY to compoundFrequency.displayValue,

                            SipThenOneTimeInvestmentDetailsConstants.TOTAL to it.sipResultData.result,
                            SipThenOneTimeInvestmentDetailsConstants.INTEREST to it.sipResultData.interest,
                            SipThenOneTimeInvestmentDetailsConstants.TOTAL_INVESTED to it.sipResultData.totalInvested,

                            SipThenOneTimeInvestmentDetailsConstants.ONETIME_AMOUNT to it.sipResultData.result,
                            SipThenOneTimeInvestmentDetailsConstants.TOTAL_FOR_ONETIME to it.oneTimeResultData.result,

                            SipThenOneTimeInvestmentDetailsConstants.TENURE_FOR_ONETIME_AFTER_SIP to yearForSipThenOneTime,
                            SipThenOneTimeInvestmentDetailsConstants.RATE_OF_RETURN_FOR_ONETIME_AFTER_SIP to interestForSipThenOneTime,
                        )
                    )
                )
            }
        }
        200.dp.SizeSpacer()
    }
}


@Composable
fun OneTimeUIDetailsAfterSip(
    content: @Composable () -> Unit
) {
    Card(
        modifier = Modifier
            .padding(top = 15.dp)
            .fillMaxWidth()
            .wrapContentHeight()
            .padding(10.dp),
        elevation = 10.dp,
        shape = RoundedCornerShape(20.dp)
    ) {
        Column(
            modifier = Modifier.padding(10.dp)
        ) {
            content()
        }
    }
}