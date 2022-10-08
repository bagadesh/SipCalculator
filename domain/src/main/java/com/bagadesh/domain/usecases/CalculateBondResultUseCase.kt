package com.bagadesh.domain.usecases

import com.bagadesh.domain.entities.BondResultData
import com.bagadesh.domain.entities.base.Currency
import com.bagadesh.domain.entities.base.Interest
import com.bagadesh.domain.entities.base.TaxRate
import com.bagadesh.domain.math.toMonthlyChunks
import com.bagadesh.domain.repository.CompoundRepository
import com.bagadesh.domain.repository.TaxRepository
import com.bagadesh.domain.requests.CalculateInterestRequest
import com.bagadesh.domain.result.Data
import com.bagadesh.domain.usecases.base.BaseUseCase
import javax.inject.Inject

/**
 * Created by bagadesh on 05/08/22.
 */
class CalculateBondResultUseCase @Inject constructor(
    private val compoundRepository: CompoundRepository,
    private val taxRepository: TaxRepository,
) : BaseUseCase<CalculateBondResultRequest, BondResultData>() {

    override suspend fun executeOnBackground(param: CalculateBondResultRequest): Data<BondResultData> {
        return when (val interestResult = compoundRepository.calculateInterest(request = param.toInterestCalculateRequest())) {
            is Data.Failure -> Data.Failure(exception = interestResult.exception)
            is Data.Success -> {
                when (val taxResult = taxRepository.calculateTax(amount = interestResult.data.interestEarned, taxRate = param.taxRate)) {
                    is Data.Failure -> Data.Failure(exception = taxResult.exception)
                    is Data.Success -> {
                        Data.Success(
                            BondResultData(
                                amount = param.amount,
                                bondRate = param.bondInterest,
                                taxRate = param.taxRate,
                                monthlyPreTaxInterest = interestResult.data.interestEarned.toMonthlyChunks(),
                                monthlyPostTaxInterest = taxResult.data.remainingAmount.toMonthlyChunks()
                            )
                        )
                    }
                }
            }
        }
    }
}

data class CalculateBondResultRequest(
    val amount: Currency,
    val bondInterest: Interest,
    val taxRate: TaxRate
)

fun CalculateBondResultRequest.toInterestCalculateRequest(): CalculateInterestRequest {
    return CalculateInterestRequest(
        amount = amount,
        interest = bondInterest,
    )
}