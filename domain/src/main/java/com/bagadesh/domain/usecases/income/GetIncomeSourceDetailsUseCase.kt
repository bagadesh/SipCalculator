package com.bagadesh.domain.usecases.income

import com.bagadesh.domain.repository.IncomeRepository
import com.bagadesh.domain.result.Data
import com.bagadesh.domain.result.income.IncomeSourceData
import com.bagadesh.domain.usecases.base.BaseUseCase
import javax.inject.Inject

/**
 * Created by bagadesh on 07/08/22.
 */
class GetIncomeSourceDetailsUseCase @Inject constructor(
    private val incomeRepository: IncomeRepository
) : BaseUseCase<GetIncomeSourceRequest, IncomeSourceData>() {

    override suspend fun executeOnBackground(param: GetIncomeSourceRequest): Data<IncomeSourceData> {
        return incomeRepository.getIncomeSourceList()
    }
}

object GetIncomeSourceRequest