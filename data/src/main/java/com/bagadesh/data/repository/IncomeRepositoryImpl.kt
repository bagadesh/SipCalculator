package com.bagadesh.data.repository

import com.bagadesh.domain.entities.base.Currency
import com.bagadesh.domain.entities.base.Percentage
import com.bagadesh.domain.repository.IncomeRepository
import com.bagadesh.domain.result.Data
import com.bagadesh.domain.result.income.IncomeSaveResult
import com.bagadesh.domain.result.income.IncomeSource
import com.bagadesh.domain.result.income.IncomeSourceData
import com.bagadesh.domain.result.income.Person
import com.bagadesh.domain.usecases.income.CalculateIncomeSaveUseCaseRequest
import javax.inject.Inject

/**
 * Created by bagadesh on 07/08/22.
 */
class IncomeRepositoryImpl @Inject constructor() : IncomeRepository {

    override suspend fun getIncomeSourceList(): Data<IncomeSourceData> {
        return try {
            return Data.Success(
                data = IncomeSourceData(
                    listOfIncomeSources = listOf(
                        IncomeSource(
                            person = Person("Bagadesh"),
                            income = Currency("1000")
                        ),
                        IncomeSource(
                            person = Person("Janani"),
                            income = Currency("2000")
                        ),
                    )
                )
            )
        } catch (exception: Exception) {
            Data.Failure(exception = exception)
        }
    }

    override suspend fun findIncomeNeededToSave(incomeSaveUseCaseRequest: CalculateIncomeSaveUseCaseRequest): Data<IncomeSaveResult> {

        return Data.Success(
            IncomeSaveResult(
                targetedAmount = incomeSaveUseCaseRequest.targetAmount,
                expectedCAGR = incomeSaveUseCaseRequest.expectedCAGR,
                targetYear = incomeSaveUseCaseRequest.targetYear,
                absoluteMonthlyIncomeNeedToSave = Currency("10000"),
                percentageMonthlyIncomeNeedToSave = Percentage(10.0)
            )
        )

    }
}