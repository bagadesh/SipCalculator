package com.bagadesh.sipcalculator.home.ui.fire

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bagadesh.domain.constants.DefaultConstants
import com.bagadesh.domain.debug.customDebugValue
import com.bagadesh.domain.entities.base.Currency
import com.bagadesh.domain.entities.base.Percentage
import com.bagadesh.domain.result.fire.FireResults
import com.bagadesh.domain.usecases.fire.CalculateFireRequest
import com.bagadesh.domain.usecases.fire.CalculateFireUseCase
import com.bagadesh.sipcalculator.entities.states.UIState
import com.bagadesh.sipcalculator.extensions.toUIResult
import com.bagadesh.sipcalculator.home.ui.DefaultRateOfInflation
import com.bagadesh.sipcalculator.home.ui.DefaultYear
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
 * Created by bagadesh on 30/08/22.
 */
@HiltViewModel
class FireViewModel @Inject constructor(
    private val calculateFireUseCase: CalculateFireUseCase
) : ViewModel() {

    var monthlyExpanse = mutableStateOf(DefaultConstants.FireConstants.MONTHLY_EXPANSE)
    var inflationRate = mutableStateOf(DefaultRateOfInflation)
    var tenure = mutableStateOf(30) // Default 30 years for projection

    var fireResult = MutableStateFlow<UIState<FireResults>>(UIState.Empty())

    fun calculate(
        monthlyExpanse: Currency,
        withdrawalPercentage: Percentage
    ) {
        customDebugValue {
            appendTag(this@FireViewModel.javaClass.simpleName)
            appendLine("monthlyExpanse = $monthlyExpanse")
            appendLine("withdrawalPercentage = $withdrawalPercentage")
        }
        viewModelScope.launch {
            fireResult.emit(
                calculateFireUseCase.toUIResult(
                    param =
                    CalculateFireRequest(
                        monthlyExpanse = monthlyExpanse,
                        withdrawalPercentage = withdrawalPercentage,
                        inflationRate = Percentage(inflationRate.value),
                        tenure = tenure.value
                    )
                )
            )
        }
    }

}
