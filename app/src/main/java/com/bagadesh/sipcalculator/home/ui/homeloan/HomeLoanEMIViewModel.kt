package com.bagadesh.sipcalculator.home.ui.homeloan

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bagadesh.domain.entities.HomeLoanEMIResultData
import com.bagadesh.domain.requests.HomeLoanEMIRequest
import com.bagadesh.domain.result.Data
import com.bagadesh.domain.usecases.CalculateHomeLoanEMIUseCase
import com.bagadesh.sipcalculator.entities.states.UIState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeLoanEMIViewModel @Inject constructor(
    private val calculateHomeLoanEMIUseCase: CalculateHomeLoanEMIUseCase
) : ViewModel() {

    var principal = mutableStateOf("10000000")
    var interestRate = mutableStateOf(7.5)
    var tenureYears = mutableStateOf(20)
    var inflationRate = mutableStateOf(6.0)
    var inflationYears = mutableStateOf(10)
    var currentRent = mutableStateOf("40000")
    var rentIncreaseRate = mutableStateOf(5.0)
    var sipInterestRate = mutableStateOf(12.0)
    var homePriceAppreciationRate = mutableStateOf(5.0)
    var loanToValueRatio = mutableStateOf(80.0)

    var resultState = MutableStateFlow<UIState<HomeLoanEMIResultData>>(UIState.Empty())
        private set

    fun calculate() {
        viewModelScope.launch(Dispatchers.Default) {
            try {
                val p = principal.value.toDoubleOrNull() ?: 0.0
                val r = interestRate.value
                val n = tenureYears.value
                val iRate = inflationRate.value
                val iYears = inflationYears.value
                val cRent = currentRent.value.toDoubleOrNull() ?: 0.0
                val rIncrease = rentIncreaseRate.value
                val sipR = sipInterestRate.value
                val hpAppreciation = homePriceAppreciationRate.value
                val ltv = loanToValueRatio.value

                val request = HomeLoanEMIRequest(
                    principal = p,
                    interestRate = r,
                    tenureYears = n,
                    inflationRate = iRate,
                    inflationYears = iYears,
                    currentRent = cRent,
                    rentIncreaseRate = rIncrease,
                    sipInterestRate = sipR,
                    homePriceAppreciationRate = hpAppreciation,
                    loanToValueRatio = ltv
                )

                when (val result = calculateHomeLoanEMIUseCase.execute(request)) {
                    is Data.Success -> {
                        resultState.emit(UIState.Success(result.data))
                    }
                    is Data.Failure -> {
                        resultState.emit(UIState.Failure(result.exception.message ?: "Unknown Error"))
                    }
                }
            } catch (e: Exception) {
                resultState.emit(UIState.Failure(e.message ?: "Error calculating"))
            }
        }
    }

    fun clearResults() {
        resultState.tryEmit(UIState.Empty())
    }
}

