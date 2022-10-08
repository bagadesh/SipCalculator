package com.bagadesh.domain.usecases.save

import com.bagadesh.domain.repository.PersistenceRepository
import com.bagadesh.domain.result.Data
import com.bagadesh.domain.usecases.base.BaseUseCase
import javax.inject.Inject

/**
 * Created by bagadesh on 21/09/22.
 */
class GetDeleteResultUseCase @Inject constructor(
    private val persistenceRepository: PersistenceRepository
) : BaseUseCase<GetDeleteResultUseCaseParam, Unit>() {

    override suspend fun executeOnBackground(param: GetDeleteResultUseCaseParam): Data<Unit> {
        return persistenceRepository.deleteInvestment(param)
    }
}

data class GetDeleteResultUseCaseParam(
    val investmentId: Int
)