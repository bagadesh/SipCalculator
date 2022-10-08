package com.bagadesh.domain.usecases.save

import com.bagadesh.domain.entities.investment.InvestmentType
import com.bagadesh.domain.repository.PersistenceRepository
import com.bagadesh.domain.result.Data
import com.bagadesh.domain.result.save.SaveResults
import com.bagadesh.domain.usecases.base.BaseUseCase
import javax.inject.Inject

/**
 * Created by bagadesh on 26/08/22.
 */
class SaveResultsUseCase @Inject constructor(
    private val persistenceRepository: PersistenceRepository
) : BaseUseCase<GetSaveResultsUseCaseRequest, SaveResults>() {

    override suspend fun executeOnBackground(param: GetSaveResultsUseCaseRequest): Data<SaveResults> {
        return persistenceRepository.saveResults(request = param)
    }
}

data class GetSaveResultsUseCaseRequest(
    val title: String,
    val investmentType: InvestmentType,
    val investmentDetails: Map<String, Any>
)