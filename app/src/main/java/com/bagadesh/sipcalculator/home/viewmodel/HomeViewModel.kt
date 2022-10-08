package com.bagadesh.sipcalculator.home.viewmodel

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bagadesh.domain.entities.featureFlag.FeatureFlagKeys
import com.bagadesh.domain.entities.investment.InvestmentType
import com.bagadesh.domain.usecases.featureFlag.FeatureFlagReaderRequest
import com.bagadesh.domain.usecases.featureFlag.GetFeatureFlagReaderUseCase
import com.bagadesh.domain.usecases.featureFlag.executeToFlow
import com.bagadesh.domain.usecases.save.GetSaveResultsUseCaseRequest
import com.bagadesh.domain.usecases.save.SaveResultsUseCase
import com.bagadesh.sipcalculator.extensions.toUIResult
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
 * Created by bagadesh on 19/07/22.
 */
@HiltViewModel
class HomeViewModel @Inject constructor(
    private val saveResultsUseCase: SaveResultsUseCase
) : ViewModel() {

    var investmentType = mutableStateOf(InvestmentType.ONE_TIME)

    fun saveResults(
        title: String,
        investmentType: InvestmentType,
        investmentDetails: Map<String, Any>
    ) {
        viewModelScope.launch {
            saveResultsUseCase.toUIResult(
                param = GetSaveResultsUseCaseRequest(
                    title = title,
                    investmentType = investmentType,
                    investmentDetails = investmentDetails
                )
            )
        }
    }

}
