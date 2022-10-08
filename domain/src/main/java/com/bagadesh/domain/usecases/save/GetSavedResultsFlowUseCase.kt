package com.bagadesh.domain.usecases.save

import com.bagadesh.domain.repository.PersistenceRepository
import com.bagadesh.domain.result.Data
import com.bagadesh.domain.result.save.PersistenceInvestmentData
import com.bagadesh.domain.usecases.base.BaseFlowUseCase
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

/**
 * Created by bagadesh on 21/09/22.
 */
class GetSavedResultsFlowUseCase @Inject constructor(
    private val persistenceRepository: PersistenceRepository
) : BaseFlowUseCase<GetSavedResultsFlowUseCaseRequest, List<PersistenceInvestmentData>>() {

    override fun executeOnBackground(param: GetSavedResultsFlowUseCaseRequest): Flow<Data<List<PersistenceInvestmentData>>> {
        return persistenceRepository.getAllSavedResultsFlow()
    }
}

object GetSavedResultsFlowUseCaseRequest