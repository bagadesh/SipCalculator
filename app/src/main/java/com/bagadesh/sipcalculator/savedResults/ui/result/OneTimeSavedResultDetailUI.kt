package com.bagadesh.sipcalculator.savedResults.ui.result

import androidx.compose.foundation.layout.*
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstrainScope
import androidx.constraintlayout.compose.ConstrainedLayoutReference
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.ConstraintLayoutScope
import com.bagadesh.domain.constants.InvestmentDetailConstants
import com.bagadesh.domain.constants.OneTimeInvestmentDetailsConstants
import com.bagadesh.domain.constants.SipInvestmentDetailsConstants
import com.bagadesh.sipcalculator.R
import com.bagadesh.sipcalculator.math.SmartMoneyRepresent
import com.bagadesh.sipcalculator.savedResults.ui.frontalIconSize
import java.math.RoundingMode

/**
 * Created by bagadesh on 03/09/22.
 */

@Composable
fun OneTimeSavedResultDetailUI(
    details: Map<String, Any>
) {
    OneTimeSavedResultDetailUI(
        total = details[OneTimeInvestmentDetailsConstants.TOTAL].toString(),
        oneTimeAmount = details[OneTimeInvestmentDetailsConstants.ONETIME_AMOUNT].toString(),
        rateOfReturn = details[OneTimeInvestmentDetailsConstants.RATE_OF_RETURN].toString(),
        tenure = details[OneTimeInvestmentDetailsConstants.TENURE].toString(),
    )
}

@Composable
fun OneTimeSavedResultDetailUI(
    total: String,
    oneTimeAmount: String,
    rateOfReturn: String,
    tenure: String,
) {
    Column(modifier = Modifier.fillMaxWidth(), verticalArrangement = Arrangement.spacedBy(10.dp)) {
        Row(modifier = Modifier.fillMaxWidth()) {
            MyUI(
                icon = R.drawable.ic_target,
                value = SmartMoneyRepresent.makeItPretty(total),
                heading = "Target",
                modifier = Modifier.weight(1f)
            )
            MyUI(
                icon = R.drawable.ic_box_money_icon,
                value = SmartMoneyRepresent.makeItPretty(oneTimeAmount),
                heading = "Invested",
                modifier = Modifier.weight(1f)
            )
        }
        MyUI(
            icon = R.drawable.ic_percentage,
            value = "${rateOfReturn.toBigDecimal().setScale(2, RoundingMode.HALF_UP).toDouble()} %",
            heading = "Rate of return",
            modifier = Modifier
        )
        MyUI(
            icon = R.drawable.ic_calendar,
            value = "${tenure.toDouble().toInt()} Years",
            heading = "Tenure",
            modifier = Modifier
        )
    }
}


@Composable
fun MyUI(
    value: String,
    icon: Int,
    heading: String,
    modifier: Modifier = Modifier
) {
    Column(modifier = modifier) {
        Row {
            Icon(
                painter = painterResource(id = icon),
                contentDescription = heading,
                modifier = Modifier
                    .padding(start = 5.dp, top = 2.dp, end = 5.dp)
                    .size(frontalIconSize),
                tint = Color.Red
            )
            Text(
                text = value,
                modifier = Modifier, fontSize = 20.sp, fontWeight = FontWeight.SemiBold
            )
        }
        Text(
            text = heading, modifier = Modifier
                .padding(5.dp), fontSize = 14.sp
        )
    }
}
