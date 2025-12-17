package com.bagadesh.sipcalculator.home.ui.inflation

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bagadesh.sipcalculator.entities.states.UIState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject
import kotlin.math.pow
import androidx.compose.runtime.State
import androidx.compose.runtime.MutableState

@HiltViewModel
class InflationViewModel @Inject constructor() : ViewModel() {

    var amount = mutableStateOf("100000")
    var inflationRate = mutableStateOf(7.0) // Default 7%
    var tenure = mutableStateOf(10) // Default 10 years

    data class InflationResult(
        val targetInflation: Double,
        val targetValue: Double,
        val minusOneValue: Double,
        val plusOneValue: Double
    )

    var resultState = MutableStateFlow<UIState<InflationResult>>(UIState.Empty())
        private set

    fun calculate() {
        viewModelScope.launch {
            try {
                val p = amount.value.toDoubleOrNull() ?: 0.0
                val r = inflationRate.value.toDouble()
                val n = tenure.value.toDouble()

                val targetVal = calculateWorth(p, r, n)
                val minusOneVal = calculateWorth(p, r - 1.0, n)
                val plusOneVal = calculateWorth(p, r + 1.0, n)

                resultState.emit(
                    UIState.Success(
                        InflationResult(
                            targetInflation = r,
                            targetValue = targetVal,
                            minusOneValue = minusOneVal,
                            plusOneValue = plusOneVal
                        )
                    )
                )
            } catch (e: Exception) {
                resultState.emit(UIState.Failure(e.message ?: "Error calculating"))
            }
        }
    }
    
    fun clearResults() {
        resultState.tryEmit(UIState.Empty())
    }

    private fun calculateWorth(principal: Double, rate: Double, years: Double): Double {
        // Worth = P / (1 + r/100)^n
        // This calculates the purchasing power of the amount in future terms
        if (rate <= -100.0) return 0.0 
        return principal / (1 + rate / 100.0).pow(years)
    }
}

