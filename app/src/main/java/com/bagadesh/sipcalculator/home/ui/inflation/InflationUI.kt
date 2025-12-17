package com.bagadesh.sipcalculator.home.ui.inflation

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
import com.bagadesh.sipcalculator.entities.states.UIState
import com.bagadesh.sipcalculator.home.ui.amount.PrincipalUI
import com.bagadesh.sipcalculator.home.ui.components.CalculateButton
import com.bagadesh.sipcalculator.home.ui.interest.RateOfInterestUI
import com.bagadesh.sipcalculator.home.ui.tenure.TenureUI
import com.bagadesh.sipcalculator.ui.base.Heading
import com.bagadesh.sipcalculator.ui.base.SizeSpacer
import com.bagadesh.sipcalculator.ui.base.UIStatePark
import com.bagadesh.sipcalculator.home.ui.result.ClearResultButton
import com.bagadesh.sipcalculator.math.SmartMoneyRepresent

@Composable
fun InflationUI(
    viewModel: InflationViewModel = hiltViewModel()
) {
    var amount by viewModel.amount
    var inflationRate by viewModel.inflationRate
    var tenure by viewModel.tenure
    val resultState by viewModel.resultState.collectAsState()

    Column(modifier = Modifier.padding(bottom = 100.dp)) {
        Heading(title = "Inflation Calculator")
        10.dp.SizeSpacer()
        
        PrincipalUI(principal = amount) { amount = it }
        30.dp.SizeSpacer()
        
        RateOfInterestUI(
            title = "Inflation Rate (%)",
            defaultInterest = inflationRate,
            maxInterest = 50.0,
            onValueChange = { inflationRate = it }
        )
        30.dp.SizeSpacer()
        
        TenureUI(value = tenure, onValueChange = { tenure = it })
        30.dp.SizeSpacer()
        
        CalculateButton { viewModel.calculate() }
        30.dp.SizeSpacer()

        UIStatePark(state = resultState) { result ->
            InflationResultCard(result)
            ClearResultButton { viewModel.clearResults() }
        }
    }
}

@Composable
fun InflationResultCard(result: InflationViewModel.InflationResult) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(Color(0xFF1E2433), RoundedCornerShape(12.dp)) 
            .padding(16.dp)
    ) {
        Text(
            text = "Value worth today's money",
            color = Color.White,
            fontSize = 18.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(bottom = 16.dp)
        )
        
        ResultRow(
            label = "At ${result.targetInflation - 1}% Inflation",
            value = SmartMoneyRepresent.formatToIndianCurrency(result.minusOneValue)
        )
        ResultRow(
            label = "At ${result.targetInflation}% Inflation",
            value = SmartMoneyRepresent.formatToIndianCurrency(result.targetValue),
            isPrimary = true
        )
        ResultRow(
            label = "At ${result.targetInflation + 1}% Inflation",
            value = SmartMoneyRepresent.formatToIndianCurrency(result.plusOneValue)
        )
    }
}

@Composable
fun ResultRow(label: String, value: String, isPrimary: Boolean = false) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(
            text = label,
            color = if (isPrimary) Color(0xFF00E5FF) else Color(0xFFB0B3B8),
            fontSize = 14.sp
        )
        Text(
            text = "₹ $value",
            color = if (isPrimary) Color(0xFF00E5FF) else Color.White,
            fontWeight = FontWeight.Bold,
            fontSize = 16.sp
        )
    }
}

