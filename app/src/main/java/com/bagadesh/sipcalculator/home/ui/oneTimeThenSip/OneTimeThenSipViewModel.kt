package com.bagadesh.sipcalculator.home.ui.oneTimeThenSip

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bagadesh.domain.entities.OneTimeThenSipResultData
import com.bagadesh.domain.result.Data
import com.bagadesh.domain.usecases.CalculateOneTimeThenSipUseCase
import com.bagadesh.domain.usecases.OneTimeThenSipUseCaseRequest
import com.bagadesh.sipcalculator.entities.states.UIState
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
 * Created by bagadesh.
 */
@HiltViewModel
class OneTimeThenSipViewModel @Inject constructor(
    private val calculateOneTimeThenSipUseCase: CalculateOneTimeThenSipUseCase
) : ViewModel() {

    var oneTimeAmount = mutableStateOf(DefaultPrincipal)
    var sipAmount = mutableStateOf(DefaultPrincipal)
    var interest = mutableStateOf(DefaultRateOfInterest)
    var year = mutableStateOf(DefaultYear)
    var compoundFrequency = mutableStateOf(CompoundFrequency.YEARLY)

    var inflationRate = mutableStateOf(DefaultRateOfInflation)
    private val inflationAdjustedReturn: Double
        get() = interest.value - inflationRate.value

    var investmentResultData: MutableStateFlow<UIState<OneTimeThenSipResultData>> = MutableStateFlow(UIState.Empty())
        private set
    var inflationAdjustedResultData: MutableStateFlow<UIState<OneTimeThenSipResultData>> = MutableStateFlow(UIState.Empty())
        private set

    fun calculate() {
        viewModelScope.launch(Dispatchers.Default) {
            investmentResultData.tryEmit(executeUseCase())
            inflationAdjustedResultData.tryEmit(executeUseCase(interestRate = inflationAdjustedReturn))
        }
    }

    fun clearResults() {
        investmentResultData.tryEmit(UIState.Empty())
        inflationAdjustedResultData.tryEmit(UIState.Empty())
    }

    private suspend fun executeUseCase(interestRate: Double = interest.value): UIState<OneTimeThenSipResultData> {
        val result = calculateOneTimeThenSipUseCase.execute(
            param = OneTimeThenSipUseCaseRequest(
                oneTimeAmount = oneTimeAmount.value,
                sipAmount = sipAmount.value,
                interest = interestRate.toString(),
                year = year.value,
                compoundFrequency = compoundFrequency.value.numValue
            )
        )
        return when (result) {
            is Data.Failure -> UIState.Failure(result.exception.convertToUIMessage())
            is Data.Success -> UIState.Success(result.data)
        }
    }
}

