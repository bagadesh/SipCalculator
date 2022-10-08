package com.bagadesh.sipcalculator.home.ui.sipThenOneTime

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bagadesh.domain.entities.SipThenOneTimeResultData
import com.bagadesh.domain.result.Data
import com.bagadesh.domain.usecases.CalculateSipResultRequest
import com.bagadesh.domain.usecases.CalculateSipThenOneTimeUseCase
import com.bagadesh.domain.usecases.SipThenOneTimeUseCaseRequest
import com.bagadesh.sipcalculator.entities.states.UIState
import com.bagadesh.sipcalculator.home.entities.CalculateInvestmentData
import com.bagadesh.sipcalculator.home.entities.InvestmentResult
import com.bagadesh.sipcalculator.home.ui.DefaultPrincipal
import com.bagadesh.sipcalculator.home.ui.DefaultRateOfInflation
import com.bagadesh.sipcalculator.home.ui.DefaultRateOfInterest
import com.bagadesh.sipcalculator.home.ui.DefaultYear
import com.bagadesh.sipcalculator.home.ui.compoundFrequency.CompoundFrequency
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
class SipThenOneTimeViewModel @Inject constructor(
    private val calculateSipThenOneTimeUseCase: CalculateSipThenOneTimeUseCase,
) : ViewModel() {

    var principal = mutableStateOf(DefaultPrincipal)
    var interest = mutableStateOf(DefaultRateOfInterest)
    var oneTimeInterest = mutableStateOf(DefaultRateOfInterest)
    var inflationRate = mutableStateOf(DefaultRateOfInflation)
    var year = mutableStateOf(DefaultYear)
    var oneTimeYear = mutableStateOf(DefaultYear)
    var compoundFrequency = mutableStateOf(CompoundFrequency.YEARLY)
    private val inflationAdjustedReturn: Int
        get() = oneTimeInterest.value - inflationRate.value

    var investmentResultData: MutableStateFlow<UIState<SipThenOneTimeResultData>> = MutableStateFlow(UIState.Empty())
        private set
    var inflationAdjustedResultData: MutableStateFlow<UIState<SipThenOneTimeResultData>> = MutableStateFlow(UIState.Empty())
        private set

    fun calculate() {
        viewModelScope.launch(Dispatchers.Default) {
            investmentResultData.tryEmit(value = executeSipThenOneTimeUseCaseCase())
            inflationAdjustedResultData.tryEmit(value = executeSipThenOneTimeUseCaseCase(oneTimeInterestRate = inflationAdjustedReturn))
        }
    }


    fun clearResults() {
        investmentResultData.tryEmit(value = UIState.Empty())
        inflationAdjustedResultData.tryEmit(value = UIState.Empty())
    }


    private suspend fun executeSipThenOneTimeUseCaseCase(oneTimeInterestRate: Int = oneTimeInterest.value): UIState<SipThenOneTimeResultData> {
        return when (
            val result = calculateSipThenOneTimeUseCase.execute(
                param = SipThenOneTimeUseCaseRequest(
                    periodicInvestment = principal.value,
                    interest = interest.value.toString(),
                    year = year.value,
                    compound = compoundFrequency.value.numValue,
                    oneTimeInterest = oneTimeInterestRate.toString(),
                    oneTimeYear = oneTimeYear.value
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