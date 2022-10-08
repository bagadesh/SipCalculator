package com.bagadesh.sipcalculator.home.ui.result

import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import com.bagadesh.domain.entities.sip.SipResultData
import com.bagadesh.domain.entities.OneTimeResultData

@Composable
fun ColumnScope.DisplayOneTimeResultUI(
    resultData: OneTimeResultData,
    title: String? = null,
    titleColor: Color = MaterialTheme.colors.primary
) {
    DisplayCommonResultUI(
        totalInvested = resultData.amount,
        interestEarned = resultData.interestEarned,
        result = resultData.result
    ) {
        ResultTitle(
            title = title ?: "One Time Result",
            titleColor = titleColor
        )
    }
}

@Composable
fun ColumnScope.DisplaySipResultUI(
    resultData: SipResultData,
    title: String? = null,
    titleColor: Color = MaterialTheme.colors.primary
) {
    DisplayCommonResultUI(
        totalInvested = resultData.totalInvested,
        interestEarned = resultData.interestEarned,
        result = resultData.result
    ) {
        ResultTitle(
            title = title ?: "SIP Result",
            titleColor = titleColor
        )
    }
}

@Composable
fun DisplayCommonResultUI(
    totalInvested: String,
    interestEarned: String,
    result: String,
    titleContent: @Composable () -> Unit
) {
    titleContent()
    HorizontalResultUI(
        list = listOf(
            HorizontalResultData(
                title = "Invested",
                value = totalInvested,
                color = Color.Blue
            ),
            HorizontalResultData(
                title = "Interest",
                value = interestEarned,
                color = Color.Yellow
            ),
            HorizontalResultData(
                title = "Total",
                value = result,
                color = Color.Green
            )
        )
    )
}