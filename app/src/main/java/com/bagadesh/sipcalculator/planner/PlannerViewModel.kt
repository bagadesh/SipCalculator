package com.bagadesh.sipcalculator.planner

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bagadesh.domain.entities.base.Currency
import com.bagadesh.domain.entities.base.Interest
import com.bagadesh.domain.entities.base.Year
import com.bagadesh.domain.result.Data
import com.bagadesh.domain.result.income.IncomeSaveResult
import com.bagadesh.domain.result.income.IncomeSource
import com.bagadesh.domain.result.income.IncomeSourceData
import com.bagadesh.domain.usecases.income.CalculateIncomeSaveUseCase
import com.bagadesh.domain.usecases.income.CalculateIncomeSaveUseCaseRequest
import com.bagadesh.domain.usecases.income.GetIncomeSourceDetailsUseCase
import com.bagadesh.domain.usecases.income.GetIncomeSourceRequest
import com.bagadesh.sipcalculator.entities.states.UIState
import com.bagadesh.sipcalculator.extensions.toFlow
import com.bagadesh.sipcalculator.extensions.toUIResult
import com.bagadesh.sipcalculator.ui.exception.convertToUIMessage
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
 * Created by bagadesh on 12/08/22.
 */
@HiltViewModel
class PlannerViewModel @Inject constructor(
    getIncomeSourceDetailsUseCase: GetIncomeSourceDetailsUseCase,
    private val getIncomeSaveUseCase: CalculateIncomeSaveUseCase
) : ViewModel() {

    val incomeSourceState =
        getIncomeSourceDetailsUseCase
            .toFlow(param = GetIncomeSourceRequest)
            .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), UIState.Empty())


    var incomeSaveResultState: MutableStateFlow<UIState<IncomeSaveResult>> = MutableStateFlow(UIState.Empty())
        private set

    fun calculateIncomeThatNeedsToBeSaved(
        incomeSourceData: IncomeSourceData,
        targetAmount: Currency,
        interest: Interest,
        targetYear: Year
    ) {
        viewModelScope.launch {
            incomeSaveResultState.emit(
                getIncomeSaveUseCase.toUIResult(
                    param = CalculateIncomeSaveUseCaseRequest(
                        targetAmount = targetAmount,
                        expectedCAGR = interest,
                        targetYear = targetYear,
                        incomeSourceData = incomeSourceData
                    )
                )
            )
        }
    }

}