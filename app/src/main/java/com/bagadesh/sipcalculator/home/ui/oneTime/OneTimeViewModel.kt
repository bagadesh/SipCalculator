package com.bagadesh.sipcalculator.home.ui.oneTime

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bagadesh.domain.entities.OneTimeResultData
import com.bagadesh.domain.result.Data
import com.bagadesh.domain.usecases.CalculateOneTimeUseCase
import com.bagadesh.domain.usecases.OneTimeResultDataRequest
import com.bagadesh.sipcalculator.entities.states.UIState
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
class OneTimeViewModel @Inject constructor(
    private val calculateOneTimeUseCase: CalculateOneTimeUseCase
) : ViewModel() {

    var principal = mutableStateOf(DefaultPrincipal)
    var interest = mutableStateOf(DefaultRateOfInterest)
    var inflationRate = mutableStateOf(DefaultRateOfInflation)
    var year = mutableStateOf(DefaultYear)
    var compoundFrequency = mutableStateOf(CompoundFrequency.YEARLY)
    private val inflationAdjustedReturn: Double
        get() = interest.value - inflationRate.value

    var investmentResultData: MutableStateFlow<UIState<OneTimeResultData>> = MutableStateFlow(UIState.Empty())
        private set
    var inflationAdjustedResultData: MutableStateFlow<UIState<OneTimeResultData>> = MutableStateFlow(UIState.Empty())
        private set

    fun calculate() {
        viewModelScope.launch(Dispatchers.Default) {
            investmentResultData.tryEmit(value = executeOneTimeUseCase())
            inflationAdjustedResultData.tryEmit(value = executeOneTimeUseCase(rateOfReturn = inflationAdjustedReturn))
        }
    }

    fun clearResults() {
        investmentResultData.tryEmit(value = UIState.Empty())
        inflationAdjustedResultData.tryEmit(value = UIState.Empty())
    }

    private suspend fun executeOneTimeUseCase(rateOfReturn: Double = interest.value): UIState<OneTimeResultData> {
        return when (
            val result = calculateOneTimeUseCase.execute(
                param = OneTimeResultDataRequest(
                    amount = principal.value,
                    interest = rateOfReturn.toString(),
                    year = year.value,
                    compounded = compoundFrequency.value.numValue
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