package com.bagadesh.sipcalculator.savedResults.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bagadesh.domain.result.save.PersistenceInvestmentData
import com.bagadesh.domain.usecases.save.GetDeleteResultUseCase
import com.bagadesh.domain.usecases.save.GetDeleteResultUseCaseParam
import com.bagadesh.domain.usecases.save.GetSavedResultsFlowUseCase
import com.bagadesh.domain.usecases.save.GetSavedResultsFlowUseCaseRequest
import com.bagadesh.sipcalculator.entities.states.UIState
import com.bagadesh.sipcalculator.extensions.toUIResult
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
 * Created by bagadesh on 02/09/22.
 */
@HiltViewModel
class SavedResultsViewModel @Inject constructor(
    private val getDeleteResultUseCase: GetDeleteResultUseCase,
    getSavedResultsFlowUseCase: GetSavedResultsFlowUseCase,
) : ViewModel() {

    val allSavedResults2: Flow<UIState<List<PersistenceInvestmentData>>> = getSavedResultsFlowUseCase.toUIResult(param = GetSavedResultsFlowUseCaseRequest)

    var expandedItems = MutableStateFlow<MutableList<String>>(mutableListOf())
        private set

    fun onExpandClick(id: String) {
        expandedItems.value = expandedItems.value.toMutableList().apply {
            if (contains(id)) {
                remove(id)
            } else {
                add(id)
            }
        }
    }

    fun onDeleteInvestment(investmentData: PersistenceInvestmentData) {
        viewModelScope.launch(Dispatchers.IO) {
            getDeleteResultUseCase.execute(param = GetDeleteResultUseCaseParam(investmentId = investmentData.id.toInt()))
        }
    }

}