package com.bagadesh.domain.usecases.cagr

import com.bagadesh.domain.entities.base.Currency
import com.bagadesh.domain.entities.base.Percentage
import com.bagadesh.domain.entities.base.Year
import com.bagadesh.domain.repository.CompoundRepository
import com.bagadesh.domain.result.Data
import com.bagadesh.domain.result.cagr.CAGRResult
import com.bagadesh.domain.usecases.base.BaseUseCase
import javax.inject.Inject

/**
 * Created by bagadesh on 21/08/22.
 */
class CalculateCARGRUseCase @Inject constructor(
    private val compoundRepository: CompoundRepository
): BaseUseCase<CalculateCAGRRequest, CAGRResult>() {

    override suspend fun executeOnBackground(param: CalculateCAGRRequest): Data<CAGRResult> {
        return compoundRepository.calculateCAGR(param)
    }
}

data class CalculateCAGRRequest(
    val initial: Currency,
    val final: Currency,
    val year: Year
)