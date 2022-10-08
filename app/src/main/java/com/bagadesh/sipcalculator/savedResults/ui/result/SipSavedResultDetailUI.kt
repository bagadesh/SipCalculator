package com.bagadesh.sipcalculator.savedResults.ui.result

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.bagadesh.domain.constants.SipInvestmentDetailsConstants
import com.bagadesh.sipcalculator.R
import com.bagadesh.sipcalculator.math.SmartMoneyRepresent
import java.math.RoundingMode

/**
 * Created by bagadesh on 03/09/22.
 */
@Composable
fun SipSavedResultDetailUI(
    details: Map<String, Any>
) {
    SipSavedResultDetailUI(
        total = details[SipInvestmentDetailsConstants.TOTAL].toString(),
        totalInvested = details[SipInvestmentDetailsConstants.TOTAL_INVESTED].toString(),
        sipAmount = details[SipInvestmentDetailsConstants.SIP_AMOUNT].toString(),
        rateOfReturn = details[SipInvestmentDetailsConstants.RATE_OF_RETURN].toString(),
        tenure = details[SipInvestmentDetailsConstants.TENURE].toString().toDouble().toInt().toString()
    )
}

@Composable
fun SipSavedResultDetailUI(
    total: String,
    totalInvested: String,
    sipAmount: String,
    rateOfReturn: String,
    tenure: String
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
                value = SmartMoneyRepresent.makeItPretty(totalInvested),
                heading = "Total Invested",
                modifier = Modifier.weight(1f)
            )
        }
        Row(modifier = Modifier.fillMaxWidth()) {
            MyUI(
                icon = R.drawable.ic_box_money_icon,
                value = SmartMoneyRepresent.makeItPretty(sipAmount),
                heading = "Sip Amount",
                modifier = Modifier.weight(1f)
            )
            MyUI(
                icon = R.drawable.ic_percentage,
                value = "${rateOfReturn.toBigDecimal().setScale(2, RoundingMode.HALF_UP).toDouble()} %",
                heading = "Rate of return",
                modifier = Modifier.weight(1f)
            )
        }
        MyUI(
            icon = R.drawable.ic_calendar,
            value = "${tenure.toDouble().toInt()} Years",
            heading = "Tenure",
            modifier = Modifier
        )
    }
}