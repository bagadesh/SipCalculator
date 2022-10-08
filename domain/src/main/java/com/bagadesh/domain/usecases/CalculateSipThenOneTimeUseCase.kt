package com.bagadesh.domain.usecases

import com.bagadesh.domain.debug.customDebugValue
import com.bagadesh.domain.entities.SipThenOneTimeResultData
import com.bagadesh.domain.repository.CompoundRepository
import com.bagadesh.domain.result.Data
import com.bagadesh.domain.usecases.base.BaseUseCase
import javax.inject.Inject

/**
 * Created by bagadesh on 05/08/22.
 */
class CalculateSipThenOneTimeUseCase @Inject constructor(
    private val compoundRepository: CompoundRepository
) : BaseUseCase<SipThenOneTimeUseCaseRequest, SipThenOneTimeResultData>() {

    override suspend fun executeOnBackground(param: SipThenOneTimeUseCaseRequest): Data<SipThenOneTimeResultData> {
        customDebugValue {
            appendTag(this@CalculateSipThenOneTimeUseCase.javaClass.simpleName)
            appendLine("SipThenOneTimeUseCaseRequest = $param")
        }
        return when (val result = compoundRepository.calculateSip(request = param.toSipRequest())) {
            is Data.Failure -> {
                Data.Failure(exception = result.exception)
            }
            is Data.Success -> {
                when (val oneTimeResult = compoundRepository.calculateOneTime(
                    request = param.toOneTimeRequest(result.data.result)
                )) {
                    is Data.Failure -> {
                        Data.Failure(exception = oneTimeResult.exception)
                    }
                    is Data.Success -> {
                        Data.Success(
                            SipThenOneTimeResultData(
                                oneTimeResultData = oneTimeResult.data,
                                sipResultData = result.data,
                            )
                        )
                    }
                }
            }
        }
    }
}

data class SipThenOneTimeUseCaseRequest(
    val periodicInvestment: String,
    val interest: String,
    val year: Int,
    val oneTimeInterest: String,
    val oneTimeYear: Int,
    val compound: Int
)

fun SipThenOneTimeUseCaseRequest.toSipRequest(): CalculateSipResultRequest {
    return CalculateSipResultRequest(
        periodicInvestment = periodicInvestment,
        interest = interest,
        year = year
    )
}

fun SipThenOneTimeUseCaseRequest.toOneTimeRequest(amount: String): OneTimeResultDataRequest {
    return OneTimeResultDataRequest(
        amount = amount,
        interest = oneTimeInterest,
        year = oneTimeYear,
        compounded = compound
    )
}