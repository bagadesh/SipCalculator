package com.bagadesh.sipcalculator.home.ui.sip

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bagadesh.domain.entities.OneTimeResultData
import com.bagadesh.domain.entities.sip.SipResultData
import com.bagadesh.domain.result.Data
import com.bagadesh.domain.usecases.CalculateSipResultRequest
import com.bagadesh.domain.usecases.CalculateSipResultUseCase
import com.bagadesh.domain.usecases.OneTimeResultDataRequest
import com.bagadesh.sipcalculator.entities.states.UIState
import com.bagadesh.sipcalculator.home.entities.CalculateInvestmentData
import com.bagadesh.sipcalculator.home.entities.InvestmentResult
import com.bagadesh.sipcalculator.home.ui.DefaultPrincipal
import com.bagadesh.sipcalculator.home.ui.DefaultRateOfInflation
import com.bagadesh.sipcalculator.home.ui.DefaultRateOfInterest
import com.bagadesh.sipcalculator.home.ui.DefaultYear
import com.bagadesh.sipcalculator.ui.exception.convertToUIMessage
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
 * Created by bagadesh on 21/08/22.
 */
@HiltViewModel
class SipViewModel @Inject constructor(
    private val calculateSipResultUseCase: CalculateSipResultUseCase,
) : ViewModel() {

    var principal = mutableStateOf(DefaultPrincipal)
    var interest = mutableStateOf(DefaultRateOfInterest)
    var inflationRate = mutableStateOf(DefaultRateOfInflation)
    var year = mutableStateOf(DefaultYear)
    private val inflationAdjustedReturn: Int
        get() = interest.value - inflationRate.value

    var investmentResultData: MutableStateFlow<UIState<SipResultData>> = MutableStateFlow(UIState.Empty())
        private set
    var inflationAdjustedResultData: MutableStateFlow<UIState<SipResultData>> = MutableStateFlow(UIState.Empty())
        private set

    fun calculate() {
        viewModelScope.launch(Dispatchers.Default) {
            investmentResultData.tryEmit(value = executeSipResultUseCase())
            inflationAdjustedResultData.tryEmit(value = executeSipResultUseCase(rateOfReturn = inflationAdjustedReturn))
        }
    }


    fun clearResults() {
        investmentResultData.tryEmit(value = UIState.Empty())
        inflationAdjustedResultData.tryEmit(value = UIState.Empty())
    }


    private suspend fun executeSipResultUseCase(rateOfReturn: Int = interest.value): UIState<SipResultData> {
        return when (
            val result = calculateSipResultUseCase.execute(
                param = CalculateSipResultRequest(
                    periodicInvestment = principal.value,
                    interest = rateOfReturn.toString(),
                    year = year.value
                )
            )
        ) {
            is Data.Failure -> {
                UIState.Failure(message = result.exception.convertToUIMessage())
            }
            is Data.Success -> {
                UIState.Success(result.data)
            }
        }
    }
}