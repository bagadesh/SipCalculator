package com.bagadesh.sipcalculator.home.ui.fire

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.bagadesh.domain.entities.base.Currency
import com.bagadesh.domain.entities.base.Percentage
import com.bagadesh.domain.misc.fire.defaultWithdrawalPercentage
import com.bagadesh.domain.result.fire.CorpusBreakdown
import com.bagadesh.domain.result.fire.FireResults
import com.bagadesh.sipcalculator.entities.states.UIState
import com.bagadesh.sipcalculator.home.ui.components.CalculateButton
import com.bagadesh.sipcalculator.home.ui.interest.RateOfInterestUI
import com.bagadesh.sipcalculator.home.ui.tenure.TenureUI
import com.bagadesh.sipcalculator.math.SmartMoneyRepresent
import com.bagadesh.sipcalculator.ui.base.ExpandableSection
import com.bagadesh.sipcalculator.ui.base.SizeSpacer
import com.bagadesh.sipcalculator.ui.base.UIStatePark
import com.bagadesh.sipcalculator.ui.base.money.MoneyRequestUI
import com.bagadesh.sipcalculator.ui.currency.DisplayCurrency
import com.bagadesh.sipcalculator.ui.custom.CenteredColumn
import com.bagadesh.sipcalculator.ui.custom.box.MyBoxUI

/**
 * Created by bagadesh on 30/08/22.
 */
@Composable
fun FireUI(
    fireViewModel: FireViewModel = hiltViewModel()
) {
    val results by fireViewModel.fireResult.collectAsState()
    var monthlyExpanse by fireViewModel.monthlyExpanse
    var inflationRate by fireViewModel.inflationRate
    var tenure by fireViewModel.tenure

    FireUIScreen(
        monthlyExpanse = monthlyExpanse,
        onMonthlyExpanseChange = { monthlyExpanse = it },
        inflationRate = inflationRate,
        onInflationRateChange = { inflationRate = it },
        tenure = tenure,
        onTenureChange = { tenure = it },
        results = results,
        onCalculateClick = { mExpanse: String, withdrawalPercentage: Int ->
            fireViewModel.calculate(
                monthlyExpanse = Currency(mExpanse),
                withdrawalPercentage = Percentage(withdrawalPercentage.toDouble())
            )
        }
    )
}

@Composable
fun FireUIScreen(
    monthlyExpanse: String,
    onMonthlyExpanseChange: (String) -> Unit,
    inflationRate: Double,
    onInflationRateChange: (Double) -> Unit,
    tenure: Int,
    onTenureChange: (Int) -> Unit,
    results: UIState<FireResults>,
    onCalculateClick: (monthlyExpanse: String, withdrawalPercentage: Int) -> Unit
) {
    var withdrawalPercentage by remember { mutableStateOf(defaultWithdrawalPercentage) }

    Column(modifier = Modifier.padding(bottom = 100.dp)) {
        MoneyRequestUI(
            title = "Monthly Expanse",
            money = monthlyExpanse,
            onValueChange = onMonthlyExpanseChange
        )
        15.dp.SizeSpacer()
        WithdrawalPercentageUI(
            withdrawalPercentage = withdrawalPercentage,
            onValueChange = {
                withdrawalPercentage = it
            }
        )
        15.dp.SizeSpacer()

        ExpandableSection(title = "Inflation Settings") {
            RateOfInterestUI(
                title = "Inflation Rate (%)",
                defaultInterest = inflationRate,
                maxInterest = 20.0,
                onValueChange = onInflationRateChange
            )
            15.dp.SizeSpacer()
            TenureUI(
                value = tenure,
                onValueChange = onTenureChange
            )
        }
        15.dp.SizeSpacer()

        CalculateButton { onCalculateClick.invoke(monthlyExpanse, withdrawalPercentage) }
        
        UIStatePark(results) {
            Column {
                MyBoxUI {
                    CenteredColumn(
                        modifier = Modifier
                            .wrapContentSize()
                            .padding(5.dp),
                        childPadding = 5.dp
                    ) {
                        Text(text = "Required corpus", fontWeight = FontWeight.Bold)
                        Text(text = SmartMoneyRepresent.makeItPretty(it.requiredCorpus.value))
                        Text(
                            fontSize = 12.sp,
                            fontWeight = FontWeight.Bold,
                            text = DisplayCurrency.display(SmartMoneyRepresent.formatToIndianCurrency(it.requiredCorpus.value))
                        )
                    }
                }
                
                if (it.yearlyCorpusBreakdown.isNotEmpty()) {
                    15.dp.SizeSpacer()
                    Text(
                        text = "Inflation Projection",
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier.padding(bottom = 10.dp)
                    )
                    
                    LazyRow(
                        horizontalArrangement = Arrangement.spacedBy(10.dp),
                        contentPadding = PaddingValues(horizontal = 4.dp)
                    ) {
                        items(it.yearlyCorpusBreakdown) { breakdown ->
                            CorpusProjectionCard(breakdown)
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun CorpusProjectionCard(breakdown: CorpusBreakdown) {
    Column(
        modifier = Modifier
            .width(140.dp)
            .background(Color(0xFF2A303E), RoundedCornerShape(8.dp))
            .padding(8.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "Year ${breakdown.year}",
            color = Color(0xFFB0B3B8),
            fontSize = 12.sp
        )
        4.dp.SizeSpacer()
        Text(
            text = SmartMoneyRepresent.makeItPretty(breakdown.requiredCorpus.value),
            color = Color.White,
            fontWeight = FontWeight.Bold,
            fontSize = 14.sp,
            textAlign = TextAlign.Center
        )
        Text(
            text = "₹ ${SmartMoneyRepresent.formatToIndianCurrency(breakdown.requiredCorpus.value)}",
            color = Color.Gray,
            fontSize = 10.sp,
            textAlign = TextAlign.Center
        )
    }
}

@Preview
@Composable
fun FireUIPreview() {
    FireUIScreen(
        monthlyExpanse = "50000",
        onMonthlyExpanseChange = {},
        inflationRate = 6.0,
        onInflationRateChange = {},
        tenure = 30,
        onTenureChange = {},
        results = UIState.Empty(),
        onCalculateClick = { _, _ -> }
    )
}
