package com.bagadesh.sipcalculator.home.ui.bond

import androidx.compose.material.Text
import androidx.compose.material.TextButton
import androidx.compose.runtime.Composable

/**
 * Created by bagadesh on 02/08/22.
 */
@Composable
fun BondInvestmentUI(
    onClick: () -> Unit
) {

    Text(text = "Invest the corpus in bonds?")
    TextButton(onClick = {
        onClick()
    }) {
        Text(text = "Show results")
    }

}