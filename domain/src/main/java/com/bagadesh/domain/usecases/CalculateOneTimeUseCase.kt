package com.bagadesh.domain.usecases

import com.bagadesh.domain.entities.OneTimeResultData
import com.bagadesh.domain.repository.CompoundRepository
import com.bagadesh.domain.result.Data
import com.bagadesh.domain.usecases.base.BaseUseCase
import javax.inject.Inject

/**
 * Created by bagadesh on 05/08/22.
 */
class CalculateOneTimeUseCase @Inject constructor(
    private val compoundRepository: CompoundRepository
) : BaseUseCase<OneTimeResultDataRequest, OneTimeResultData>() {

    override suspend fun executeOnBackground(param: OneTimeResultDataRequest): Data<OneTimeResultData> {
        return compoundRepository.calculateOneTime(request = param)
    }
}

data class OneTimeResultDataRequest(
    val amount: String,
    val interest: String,
    val year: Int,
    val compounded: Int
)