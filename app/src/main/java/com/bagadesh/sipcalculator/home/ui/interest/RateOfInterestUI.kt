package com.bagadesh.sipcalculator.home.ui.interest

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Slider
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp

/**
 * Created by bagadesh on 23/07/22.
 */

@Composable
fun RateOfInterestUI(
    maxInterest: Int,
    title: String = "Interest Rate",
    defaultInterest: Int,
    onValueChange: (Int) -> Unit
) {
    var interestValue by remember {
        mutableStateOf(defaultInterest.toFloat())
    }
    Text(
        text = buildAnnotatedString {
            withStyle(SpanStyle(fontWeight = FontWeight.SemiBold)) {
                append(title)
            }
            append(" - ${interestValue.toInt()}%")
        },
        modifier = Modifier.padding(top = 10.dp)
    )
    Slider(
        value = interestValue,
        valueRange = 1f..maxInterest.toFloat(),
        onValueChange = {
            interestValue = it
            onValueChange(it.toInt())
        },
        modifier = Modifier
            .fillMaxWidth()
            .padding(
                top = 5.dp,
                bottom = 0.dp,
            ),
        steps = 0
    )
    Row(
        modifier = Modifier
            .fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        for (i in 0..maxInterest step 10) {
            Text(
                text = (if (i == 0) i + 1 else i).toString(),
            )
        }
    }
}