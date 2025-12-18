package com.bagadesh.domain.usecases.fire

import com.bagadesh.domain.entities.base.Currency
import com.bagadesh.domain.entities.base.Percentage
import com.bagadesh.domain.repository.FireRepository
import com.bagadesh.domain.result.Data
import com.bagadesh.domain.result.fire.FireResults
import com.bagadesh.domain.usecases.base.BaseUseCase
import javax.inject.Inject

/**
 * Created by bagadesh on 30/08/22.
 */
class CalculateFireUseCase @Inject constructor(
    private val fireRepository: FireRepository
) : BaseUseCase<CalculateFireRequest, FireResults>() {

    override suspend fun executeOnBackground(param: CalculateFireRequest): Data<FireResults> {
        return fireRepository.calculateCorpus(request = param)
    }
}


data class CalculateFireRequest(
    val monthlyExpanse: Currency,
    val withdrawalPercentage: Percentage,
    val inflationRate: Percentage,
    val tenure: Int
)
