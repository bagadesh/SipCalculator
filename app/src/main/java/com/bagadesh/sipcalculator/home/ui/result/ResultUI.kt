package com.bagadesh.sipcalculator.home.ui.result

import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.bagadesh.domain.entities.OneTimeResultData
import com.bagadesh.domain.entities.sip.SipResultData
import com.bagadesh.sipcalculator.R
import com.bagadesh.sipcalculator.entities.states.UIState
import com.bagadesh.sipcalculator.extensions.isSuccess
import com.bagadesh.sipcalculator.home.entities.InvestmentResult

/**
 * Created by bagadesh on 05/08/22.
 */
@Composable
fun ResultUI(
    normalResult: UIState<InvestmentResult>,
    inflationAdjustedResult: UIState<InvestmentResult>,
    onSaveClick: (InvestmentResult) -> Unit = {},
    onClearClick: () -> Unit
) {
    Column(modifier = Modifier.animateContentSize()) {
        when (normalResult) {
            is UIState.Empty -> {}
            is UIState.Failure -> {
                FailureUI(normalResult)
            }
            is UIState.Success -> {
                when (val investmentResult = normalResult.data) {
                    is InvestmentResult.OneTimeResult -> {
                        ShowOneTimeUI(resultData = investmentResult.oneTimeResultData, inflationAdjustedResult = inflationAdjustedResult)
                    }
                    is InvestmentResult.SipResult -> {
                        ShowSipResultUI(resultData = investmentResult.sipResultData, inflationAdjustedResult = inflationAdjustedResult)
                    }
                    is InvestmentResult.SipThenOneTime -> {
                        ShowSipResultUI(resultData = investmentResult.sipThenOneTimeResultData.sipResultData)
                        ShowDownArrowUI()
                        ShowOneTimeUI(
                            resultData = investmentResult.sipThenOneTimeResultData.oneTimeResultData,
                            inflationAdjustedResult = inflationAdjustedResult
                        )
                    }
                }
                CustomButton(
                    modifier = Modifier.align(Alignment.CenterHorizontally),
                    onClick = {
                        onClearClick()
                    },
                    text = "Clear results"
                )
                CustomButton(
                    modifier = Modifier.align(Alignment.CenterHorizontally),
                    onClick = {
                        onSaveClick(normalResult.data)
                    },
                    text = "Save results"
                )
            }
        }

    }
}

@Composable
fun ColumnScope.ShowOneTimeUI(
    resultData: OneTimeResultData,
    inflationAdjustedResult: UIState<InvestmentResult>
) {
    DisplayOneTimeResultUI(resultData = resultData, titleColor = MaterialTheme.colors.primary)
    inflationAdjustedResult.isSuccess {
        when (it.data) {
            is InvestmentResult.OneTimeResult -> {
                it.data.oneTimeResultData
            }
            is InvestmentResult.SipThenOneTime -> {
                it.data.sipThenOneTimeResultData.oneTimeResultData
            }
            else -> {
                null
            }
        }?.let { result ->
            ShowDownArrowUI()
            DisplayOneTimeResultUI(
                resultData = result,
                title = "Inflation Adjusted Return"
            )
        }
    }
}
@Composable
fun ColumnScope.ShowOneTimeUIInflationAdjustedData(
    inflationAdjustedResult: OneTimeResultData,
) {
    ShowDownArrowUI()
    DisplayOneTimeResultUI(
        resultData = inflationAdjustedResult,
        title = "Inflation Adjusted Return"
    )
}

@Composable
fun ColumnScope.ShowSipResultUI(
    resultData: SipResultData,
    inflationAdjustedResult: UIState<InvestmentResult>? = null,
) {
    DisplaySipResultUI(resultData = resultData, titleColor = MaterialTheme.colors.primary)
    inflationAdjustedResult?.isSuccess {
        when (it.data) {
            is InvestmentResult.SipResult -> {
                it.data.sipResultData
            }
            else -> if (it.data is InvestmentResult.SipThenOneTime) {
                it.data.sipThenOneTimeResultData.sipResultData
            } else {
                null
            }
        }?.let { result ->
            ShowDownArrowUI()
            DisplaySipResultUI(
                resultData = result,
                title = "Inflation Adjusted Return"
            )
        }
    }
}

@Composable
fun ColumnScope.ShowDownArrowUI() {
    Icon(
        painter = painterResource(id = R.drawable.ic_down_arrow),
        modifier = Modifier
            .padding(10.dp)
            .size(32.dp)
            .align(Alignment.CenterHorizontally),
        contentDescription = "Down Arrow",
        tint = MaterialTheme.colors.primary
    )
}

