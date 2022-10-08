package com.bagadesh.sipcalculator.home.ui.fire

import androidx.compose.foundation.layout.*
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.bagadesh.domain.entities.base.Currency
import com.bagadesh.domain.entities.base.Percentage
import com.bagadesh.domain.misc.fire.defaultWithdrawalPercentage
import com.bagadesh.domain.result.fire.FireResults
import com.bagadesh.sipcalculator.entities.states.UIState
import com.bagadesh.sipcalculator.home.ui.components.CalculateButton
import com.bagadesh.sipcalculator.math.SmartMoneyRepresent
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
    FireUIScreen(
        defaultMonthlyExpanse = fireViewModel.monthlyExpanse,
        results = results,
        onCalculateClick = { monthlyExpanse: String, withdrawalPercentage: Int ->
            fireViewModel.calculate(
                monthlyExpanse = Currency(monthlyExpanse),
                withdrawalPercentage = Percentage(withdrawalPercentage.toDouble())
            )
        }
    )
}

@Composable
fun FireUIScreen(
    defaultMonthlyExpanse: MutableState<String>,
    results: UIState<FireResults>,
    onCalculateClick: (monthlyExpanse: String, withdrawalPercentage: Int) -> Unit
) {
    var monthlyExpanse by defaultMonthlyExpanse
    var withdrawalPercentage by remember { mutableStateOf(defaultWithdrawalPercentage) }

    Column {
        MoneyRequestUI(
            title = "Monthly Expanse",
            money = monthlyExpanse,
            onValueChange = {
                monthlyExpanse = it
            }
        )
        15.dp.SizeSpacer()
        WithdrawalPercentageUI(
            withdrawalPercentage = withdrawalPercentage,
            onValueChange = {
                withdrawalPercentage = it
            }
        )
        CalculateButton { onCalculateClick.invoke(monthlyExpanse, withdrawalPercentage) }
        UIStatePark(results) {
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
                        text = DisplayCurrency.display(SmartMoneyRepresent.makeItIntegerWithoutDecimal(it.requiredCorpus.value))
                    )
                }
            }
        }
    }
}

@Preview
@Composable
fun FireUIPreview() {
    FireUIScreen(
        results = UIState.Empty(),
        onCalculateClick = { _, _ ->
        },
        defaultMonthlyExpanse = remember {
            mutableStateOf("50000")
        }
    )
}

