package com.bagadesh.domain.usecases.save

import com.bagadesh.domain.repository.PersistenceRepository
import com.bagadesh.domain.result.Data
import com.bagadesh.domain.result.save.SavedResults
import com.bagadesh.domain.usecases.base.BaseUseCase
import javax.inject.Inject

/**
 * Created by bagadesh on 02/09/22.
 */
class GetAllSavedResultsUseCase @Inject constructor(
    private val persistenceRepository: PersistenceRepository
) : BaseUseCase<GetSavedResultsUseCaseRequest, SavedResults>() {

    override suspend fun executeOnBackground(param: GetSavedResultsUseCaseRequest): Data<SavedResults> {
        return persistenceRepository.getAllSavedResults()
    }
}

object GetSavedResultsUseCaseRequest