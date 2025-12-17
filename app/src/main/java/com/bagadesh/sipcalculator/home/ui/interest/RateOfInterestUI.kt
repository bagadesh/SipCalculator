package com.bagadesh.sipcalculator.home.ui.interest

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Icon
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import com.bagadesh.sipcalculator.R

/**
 * Created by bagadesh on 23/07/22.
 */

@Composable
fun RateOfInterestUI(
    title: String = "Interest Rate",
    defaultInterest: Double,
    maxInterest: Double = 100.0,
    onValueChange: (Double) -> Unit
) {
    var interestValue by remember(defaultInterest) {
        mutableStateOf(if (defaultInterest == 0.0) "" else defaultInterest.toString())
    }

    Text(
        text = title,
        modifier = Modifier.padding(top = 10.dp, start = 5.dp),
        fontWeight = FontWeight.SemiBold
    )

    OutlinedTextField(
        value = interestValue,
        onValueChange = { newValue ->
            if (newValue.isEmpty()) {
                interestValue = ""
                onValueChange(0.0)
                return@OutlinedTextField
            }

            // Regex to allow only numbers and up to 2 decimal places
            // Matches:
            // 12
            // 12.
            // 12.3
            // 12.34
            val regex = Regex("^\\d*\\.?\\d{0,2}$")
            
            if (regex.matches(newValue)) {
                val doubleValue = newValue.toDoubleOrNull()
                if (doubleValue != null && doubleValue <= maxInterest) {
                    interestValue = newValue
                    onValueChange(doubleValue)
                } else if (doubleValue == null && newValue == ".") {
                    // Allow starting with decimal point temporarily
                    interestValue = newValue
                }
            }
        },
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 10.dp),
        trailingIcon = {
            Icon(
                painter = painterResource(id = R.drawable.ic_percentage),
                contentDescription = "Percentage",
                modifier = Modifier.padding(10.dp)
            )
        },
        shape = RoundedCornerShape(10.dp),
        keyboardOptions = KeyboardOptions(
            keyboardType = KeyboardType.Decimal
        ),
        singleLine = true
    )
}
