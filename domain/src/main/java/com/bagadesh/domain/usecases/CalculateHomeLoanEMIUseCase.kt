package com.bagadesh.domain.usecases

import com.bagadesh.domain.entities.HomeLoanEMIResultData
import com.bagadesh.domain.repository.CompoundRepository
import com.bagadesh.domain.requests.HomeLoanEMIRequest
import com.bagadesh.domain.result.Data
import com.bagadesh.domain.usecases.base.BaseUseCase
import javax.inject.Inject

class CalculateHomeLoanEMIUseCase @Inject constructor(
    private val compoundRepository: CompoundRepository
) : BaseUseCase<HomeLoanEMIRequest, HomeLoanEMIResultData>() {

    override suspend fun executeOnBackground(param: HomeLoanEMIRequest): Data<HomeLoanEMIResultData> {
        return compoundRepository.calculateHomeLoanEMI(request = param)
    }
}

