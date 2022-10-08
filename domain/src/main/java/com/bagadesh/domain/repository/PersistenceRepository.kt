package com.bagadesh.domain.repository

import com.bagadesh.domain.result.Data
import com.bagadesh.domain.result.save.PersistenceInvestmentData
import com.bagadesh.domain.result.save.SaveResults
import com.bagadesh.domain.result.save.SavedResults
import com.bagadesh.domain.usecases.save.GetDeleteResultUseCase
import com.bagadesh.domain.usecases.save.GetDeleteResultUseCaseParam
import com.bagadesh.domain.usecases.save.GetSaveResultsUseCaseRequest
import kotlinx.coroutines.flow.Flow

/**
 * Created by bagadesh on 26/08/22.
 */
interface PersistenceRepository {

    fun saveResults(request: GetSaveResultsUseCaseRequest): Data<SaveResults>

    fun getAllSavedResults(): Data<SavedResults>

    fun getAllSavedResultsFlow(): Flow<Data<List<PersistenceInvestmentData>>>

    fun deleteInvestment(request: GetDeleteResultUseCaseParam): Data<Unit>

}