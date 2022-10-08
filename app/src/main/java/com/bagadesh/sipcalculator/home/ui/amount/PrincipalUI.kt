package com.bagadesh.sipcalculator.home.ui.amount

import androidx.compose.runtime.Composable
import com.bagadesh.sipcalculator.ui.base.money.MoneyRequestUI

/**
 * Created by bagadesh on 23/07/22.
 */

@Composable
fun PrincipalUI(
    principal: String,
    onValueChange: (String) -> Unit
) {
    MoneyRequestUI(
        title = "Principal",
        money = principal,
        onValueChange = onValueChange
    )
}