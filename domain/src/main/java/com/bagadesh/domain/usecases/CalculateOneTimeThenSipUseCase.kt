package com.bagadesh.domain.usecases

import com.bagadesh.domain.entities.OneTimeThenSipResultData
import com.bagadesh.domain.entities.base.Currency
import com.bagadesh.domain.math.plus
import com.bagadesh.domain.repository.CompoundRepository
import com.bagadesh.domain.result.Data
import com.bagadesh.domain.usecases.base.BaseUseCase
import javax.inject.Inject

/**
 * Created by bagadesh.
 */
class CalculateOneTimeThenSipUseCase @Inject constructor(
    private val compoundRepository: CompoundRepository
) : BaseUseCase<OneTimeThenSipUseCaseRequest, OneTimeThenSipResultData>() {

    override suspend fun executeOnBackground(param: OneTimeThenSipUseCaseRequest): Data<OneTimeThenSipResultData> {
        val sipResult = compoundRepository.calculateSip(request = param.toSipRequest())
        if (sipResult is Data.Failure) return Data.Failure(sipResult.exception)

        val oneTimeResult = compoundRepository.calculateOneTime(request = param.toOneTimeRequest())
        if (oneTimeResult is Data.Failure) return Data.Failure(oneTimeResult.exception)

        val sipData = (sipResult as Data.Success).data
        val oneTimeData = (oneTimeResult as Data.Success).data

        val total = Currency(sipData.result) + Currency(oneTimeData.result)

        return Data.Success(
            OneTimeThenSipResultData(
                sipResultData = sipData,
                oneTimeResultData = oneTimeData,
                totalResult = total.value
            )
        )
    }
}

data class OneTimeThenSipUseCaseRequest(
    val oneTimeAmount: String,
    val sipAmount: String,
    val interest: String,
    val year: Int,
    val compoundFrequency: Int
)

fun OneTimeThenSipUseCaseRequest.toSipRequest(): CalculateSipResultRequest {
    return CalculateSipResultRequest(
        periodicInvestment = sipAmount,
        interest = interest,
        year = year
    )
}

fun OneTimeThenSipUseCaseRequest.toOneTimeRequest(): OneTimeResultDataRequest {
    return OneTimeResultDataRequest(
        amount = oneTimeAmount,
        interest = interest,
        year = year,
        compounded = compoundFrequency
    )
}

