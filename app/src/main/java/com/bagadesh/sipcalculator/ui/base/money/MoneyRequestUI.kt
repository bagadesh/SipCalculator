package com.bagadesh.sipcalculator.ui.base.money

import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Icon
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.bagadesh.sipcalculator.R
import com.bagadesh.sipcalculator.math.SmartMoneyRepresent.makeItPretty
import com.bagadesh.sipcalculator.ui.currency.DisplayCurrency

/**
 * Created by bagadesh on 17/08/22.
 */
@Composable
fun MoneyRequestUI(
    title: String,
    money: String,
    enableSmartMoney: Boolean = true,
    onValueChange: (String) -> Unit
) {
    val focusManager = LocalFocusManager.current
    val focusRequester = remember { FocusRequester() }
    Text(
        text = title,
        modifier = Modifier.padding(top = 10.dp, start = 5.dp),
        fontWeight = FontWeight.SemiBold
    )
    OutlinedTextField(
        value = money,
        onValueChange = {
            onValueChange(it)
        },
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 10.dp)
            .focusRequester(focusRequester),
        leadingIcon = {
            Text(text = DisplayCurrency.currentCurrency())
        },
        trailingIcon = {
            Icon(
                painter = painterResource(id = R.drawable.ic_baseline_cancel_24),
                contentDescription = "Clear",
                modifier = Modifier
                    .clickable {
                        onValueChange("")
                        focusRequester.requestFocus()
                    }
                    .padding(10.dp)
            )
        },
        shape = RoundedCornerShape(10.dp),
        keyboardOptions = KeyboardOptions(
            keyboardType = KeyboardType.Number,
            imeAction = ImeAction.Done
        ),
        keyboardActions = KeyboardActions(onDone = {
            focusManager.clearFocus()
        }),
        singleLine = true,
    )
    if (enableSmartMoney) {
        SmartMoneyRepresent(money)
    }
}


@Composable
fun SmartMoneyRepresent(
    money: String
) {
    Text(
        text = makeItPretty(value = money),
        fontSize = 14.sp,
        modifier = Modifier.animateContentSize().padding(top = 10.dp, start = 10.dp)
    )
}