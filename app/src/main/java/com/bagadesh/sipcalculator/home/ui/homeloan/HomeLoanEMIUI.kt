package com.bagadesh.sipcalculator.home.ui.homeloan

import androidx.compose.foundation.lazy.LazyRow
import com.bagadesh.domain.entities.InflationBreakdown
import androidx.compose.foundation.lazy.items
import androidx.compose.ui.Alignment
import androidx.compose.ui.text.style.TextAlign
import com.bagadesh.domain.entities.RentBreakdown
import com.bagadesh.sipcalculator.math.SmartMoneyRepresent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.bagadesh.domain.entities.HomeLoanEMIResultData
import com.bagadesh.sipcalculator.home.ui.amount.PrincipalUI
import com.bagadesh.sipcalculator.home.ui.components.CalculateButton
import com.bagadesh.sipcalculator.home.ui.inflation.ResultRow
import com.bagadesh.sipcalculator.home.ui.interest.RateOfInterestUI
import com.bagadesh.sipcalculator.home.ui.result.ClearResultButton
import com.bagadesh.sipcalculator.home.ui.tenure.TenureUI
import com.bagadesh.sipcalculator.ui.base.Heading
import com.bagadesh.sipcalculator.ui.base.SizeSpacer
import com.bagadesh.sipcalculator.ui.base.UIStatePark
import com.bagadesh.sipcalculator.ui.base.money.MoneyRequestUI

import com.bagadesh.sipcalculator.ui.base.ExpandableSection

import com.bagadesh.domain.entities.SipBreakdown

@Composable
fun HomeLoanEMIUI(
    viewModel: HomeLoanEMIViewModel = hiltViewModel()
) {
    var principal by viewModel.principal
    var interestRate by viewModel.interestRate
    var tenureYears by viewModel.tenureYears
    var inflationRate by viewModel.inflationRate
    var inflationYears by viewModel.inflationYears
    var currentRent by viewModel.currentRent
    var rentIncreaseRate by viewModel.rentIncreaseRate
    var sipInterestRate by viewModel.sipInterestRate
    val resultState by viewModel.resultState.collectAsState()

    Column(modifier = Modifier.padding(bottom = 100.dp)) {
        Heading(title = "Home Loan EMI Calculator")
        10.dp.SizeSpacer()

        PrincipalUI(principal = principal) { principal = it }
        30.dp.SizeSpacer()

        RateOfInterestUI(
            title = "Interest Rate (%)",
            defaultInterest = interestRate.toDouble(),
            maxInterest = 20.0,
            onValueChange = { interestRate = it }
        )
        30.dp.SizeSpacer()

        TenureUI(value = tenureYears, onValueChange = { tenureYears = it })
        30.dp.SizeSpacer()

        ExpandableSection(title = "Rent Comparison") {
            10.dp.SizeSpacer()

            MoneyRequestUI(
                title = "Current Monthly Rent",
                money = currentRent,
                onValueChange = { currentRent = it }
            )
            30.dp.SizeSpacer()

            RateOfInterestUI(
                title = "Rent Increase Rate (%)",
                defaultInterest = rentIncreaseRate.toDouble(),
                maxInterest = 20.0,
                onValueChange = { rentIncreaseRate = it.toDouble() }
            )
            30.dp.SizeSpacer()
        }

        ExpandableSection(title = "Inflation Settings") {
            10.dp.SizeSpacer()

            RateOfInterestUI(
                title = "Inflation Rate (%)",
                defaultInterest = inflationRate.toDouble(),
                maxInterest = 20.0,
                onValueChange = { inflationRate = it }
            )
            30.dp.SizeSpacer()
            
            TenureUI(value = inflationYears, onValueChange = { inflationYears = it })
            Text(text = "Adjusted after $inflationYears years", fontSize = 12.sp, color = Color.Gray)
            30.dp.SizeSpacer()
        }

        ExpandableSection(title = "SIP Settings") {
            10.dp.SizeSpacer()
            RateOfInterestUI(
                title = "SIP Interest Rate (%)",
                defaultInterest = sipInterestRate.toDouble(),
                maxInterest = 30.0,
                onValueChange = { sipInterestRate = it }
            )
            30.dp.SizeSpacer()
        }

        CalculateButton { viewModel.calculate() }
        30.dp.SizeSpacer()

        UIStatePark(state = resultState) { result ->
            HomeLoanResultCard(result, inflationYears)
            ClearResultButton { viewModel.clearResults() }
        }
    }
}


@Composable
fun HomeLoanResultCard(result: HomeLoanEMIResultData, inflationYears: Int) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(Color(0xFF1E2433), RoundedCornerShape(12.dp))
            .padding(16.dp)
    ) {
        // ... (existing EMI Breakdown) ...
        Text(
            text = "EMI Breakdown",
            color = Color.White,
            fontSize = 18.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(bottom = 16.dp)
        )

        ResultRow(
            label = "Monthly EMI",
            value = SmartMoneyRepresent.formatToIndianCurrency(result.emi),
            isPrimary = true
        )
        ResultRow(
            label = "Total Interest",
            value = SmartMoneyRepresent.formatToIndianCurrency(result.totalInterest)
        )
        ResultRow(
            label = "Total Payment",
            value = SmartMoneyRepresent.formatToIndianCurrency(result.totalPayment)
        )
        
        androidx.compose.material.Divider(
            color = Color.Gray, 
            thickness = 1.dp,
            modifier = Modifier.padding(vertical = 10.dp)
        )
        
        Text(
            text = "Inflation Adjusted (Value after $inflationYears yrs)",
            color = Color(0xFFB0B3B8),
            fontSize = 14.sp,
            modifier = Modifier.padding(bottom = 8.dp)
        )
         ResultRow(
            label = "Real Value of EMI",
            value = SmartMoneyRepresent.formatToIndianCurrency(result.inflationAdjustedEMI),
            isPrimary = true
        )

        androidx.compose.material.Divider(
            color = Color.Gray,
            thickness = 1.dp,
            modifier = Modifier.padding(vertical = 10.dp)
        )


        // Inflation Projection Section
        Text(
            text = "Real Value of EMI (Inflation)",
            color = Color.White,
            fontSize = 18.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(bottom = 16.dp)
        )

        LazyRow(
            horizontalArrangement = Arrangement.spacedBy(10.dp),
            contentPadding = PaddingValues(horizontal = 4.dp)
        ) {
            items(result.yearlyInflationBreakdown) { breakdown ->
                InflationProjectionCard(breakdown)
            }
        }

        10.dp.SizeSpacer()

        // Rent Projection Section
        Text(
            text = "Rent Projection",
            color = Color.White,
            fontSize = 18.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(bottom = 16.dp)
        )

        LazyRow(
            horizontalArrangement = Arrangement.spacedBy(10.dp),
            contentPadding = PaddingValues(horizontal = 4.dp)
        ) {
            items(result.yearlyRentBreakdown) { breakdown ->
                RentProjectionCard(breakdown)
            }
        }

        androidx.compose.material.Divider(
            color = Color.Gray,
            thickness = 1.dp,
            modifier = Modifier.padding(vertical = 10.dp)
        )

        // SIP Projection Section
        Text(
            text = "SIP Projection",
            color = Color.White,
            fontSize = 18.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(bottom = 16.dp)
        )

        LazyRow(
            horizontalArrangement = Arrangement.spacedBy(10.dp),
            contentPadding = PaddingValues(horizontal = 4.dp)
        ) {
            items(result.yearlySipBreakdown) { breakdown ->
                SipProjectionCard(breakdown)
            }
        }

    }
}

@Composable
fun SipProjectionCard(breakdown: SipBreakdown) {
    Column(
        modifier = Modifier
            .width(160.dp)
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
            text = "₹ ${SmartMoneyRepresent.formatToIndianCurrency(breakdown.projectedValue)}",
            color = Color.White,
            fontWeight = FontWeight.Bold,
            fontSize = 14.sp,
            textAlign = TextAlign.Center
        )
        2.dp.SizeSpacer()
        Text(
            text = "Invested: ₹ ${SmartMoneyRepresent.formatToIndianCurrency(breakdown.investableAmountMonthly)}/m",
            color = Color.Gray,
            fontSize = 10.sp,
            textAlign = TextAlign.Center
        )
    }
}

@Composable
fun InflationProjectionCard(breakdown: InflationBreakdown) {
    Column(
        modifier = Modifier
            .width(120.dp)
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
            text = "₹ ${SmartMoneyRepresent.formatToIndianCurrency(breakdown.inflationAdjustedEmi)}",
            color = Color.White,
            fontWeight = FontWeight.Bold,
            fontSize = 14.sp,
            textAlign = TextAlign.Center
        )
        2.dp.SizeSpacer()
        Text(
            text = "real worth",
            color = Color.Gray,
            fontSize = 10.sp
        )
    }
}

@Composable
fun RentProjectionCard(breakdown: RentBreakdown) {
    Column(
        modifier = Modifier
            .width(120.dp)
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
            text = "₹ ${SmartMoneyRepresent.formatToIndianCurrency(breakdown.monthlyRent)}",
            color = Color.White,
            fontWeight = FontWeight.Bold,
            fontSize = 14.sp,
            textAlign = TextAlign.Center
        )
        2.dp.SizeSpacer()
        Text(
            text = "/month",
            color = Color.Gray,
            fontSize = 10.sp
        )
    }
}

