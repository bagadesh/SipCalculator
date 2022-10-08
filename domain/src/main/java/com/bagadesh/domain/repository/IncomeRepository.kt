package com.bagadesh.domain.repository

import com.bagadesh.domain.result.Data
import com.bagadesh.domain.result.income.IncomeSaveResult
import com.bagadesh.domain.result.income.IncomeSourceData
import com.bagadesh.domain.usecases.income.CalculateIncomeSaveUseCaseRequest

/**
 * Created by bagadesh on 07/08/22.
 */
interface IncomeRepository {

    suspend fun getIncomeSourceList(): Data<IncomeSourceData>

    suspend fun findIncomeNeededToSave(incomeSaveUseCaseRequest: CalculateIncomeSaveUseCaseRequest): Data<IncomeSaveResult>

}