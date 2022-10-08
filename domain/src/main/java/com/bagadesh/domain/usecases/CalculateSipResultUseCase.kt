package com.bagadesh.domain.usecases

import com.bagadesh.domain.entities.sip.SipResultData
import com.bagadesh.domain.repository.CompoundRepository
import com.bagadesh.domain.result.Data
import com.bagadesh.domain.usecases.base.BaseUseCase
import javax.inject.Inject

/**
 * Created by bagadesh on 02/08/22.
 */

class CalculateSipResultUseCase @Inject constructor(
    private val compoundRepository: CompoundRepository
): BaseUseCase<CalculateSipResultRequest, SipResultData>() {

    override suspend fun executeOnBackground(param: CalculateSipResultRequest): Data<SipResultData> {
        return compoundRepository.calculateSip(request = param)
    }
}

data class CalculateSipResultRequest(
    val periodicInvestment: String,
    val interest: String,
    val year: Int,
)