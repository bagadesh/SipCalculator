package com.bagadesh.sipcalculator.home.ui.cagr

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bagadesh.domain.constants.DefaultConstants
import com.bagadesh.domain.entities.base.Currency
import com.bagadesh.domain.entities.base.Percentage
import com.bagadesh.domain.entities.base.Year
import com.bagadesh.domain.result.cagr.CAGRResult
import com.bagadesh.domain.usecases.cagr.CalculateCAGRRequest
import com.bagadesh.domain.usecases.cagr.CalculateCARGRUseCase
import com.bagadesh.sipcalculator.entities.states.UIState
import com.bagadesh.sipcalculator.extensions.toUIResult
import com.bagadesh.sipcalculator.home.ui.DefaultPrincipal
import com.bagadesh.sipcalculator.home.ui.DefaultRateOfInflation
import com.bagadesh.sipcalculator.home.ui.DefaultRateOfInterest
import com.bagadesh.sipcalculator.home.ui.DefaultYear
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
 * Created by bagadesh on 21/08/22.
 */
@HiltViewModel
class CAGRViewModel @Inject constructor(
    private val calculateCARGRUseCase: CalculateCARGRUseCase
) : ViewModel() {

    var initial = mutableStateOf(DefaultConstants.CagrConstants.INITIAL)
    var final = mutableStateOf(DefaultConstants.CagrConstants.FINAL)
    var year = mutableStateOf(DefaultConstants.CagrConstants.YEAR)

    var cagrResult: MutableStateFlow<UIState<CAGRResult>> = MutableStateFlow(UIState.Empty())
        private set

    fun calculate() {
        viewModelScope.launch(Dispatchers.Default) {
            cagrResult.emit(
                calculateCARGRUseCase.toUIResult(
                    param = CalculateCAGRRequest(
                        initial = Currency(initial.value),
                        final = Currency(final.value),
                        year = Year(year.value.toDouble())
                    )
                )
            )
        }
    }

}