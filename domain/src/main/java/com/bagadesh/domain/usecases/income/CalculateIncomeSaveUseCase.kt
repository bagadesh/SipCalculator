package com.bagadesh.domain.usecases.income

import com.bagadesh.domain.entities.base.Currency
import com.bagadesh.domain.entities.base.Interest
import com.bagadesh.domain.entities.base.Year
import com.bagadesh.domain.repository.IncomeRepository
import com.bagadesh.domain.result.Data
import com.bagadesh.domain.result.income.IncomeSaveResult
import com.bagadesh.domain.result.income.IncomeSourceData
import com.bagadesh.domain.usecases.base.BaseUseCase
import javax.inject.Inject

/**
 * Created by bagadesh on 17/08/22.
 */
class CalculateIncomeSaveUseCase @Inject constructor(
    private val incomeRepository: IncomeRepository
) : BaseUseCase<CalculateIncomeSaveUseCaseRequest, IncomeSaveResult>() {

    override suspend fun executeOnBackground(param: CalculateIncomeSaveUseCaseRequest): Data<IncomeSaveResult> {
        return incomeRepository.findIncomeNeededToSave(param)
    }
}


data class CalculateIncomeSaveUseCaseRequest(
    val targetAmount: Currency,
    val expectedCAGR: Interest,
    val targetYear: Year,
    val incomeSourceData: IncomeSourceData
)