package com.bagadesh.data.repository

import ch.obermuhlner.math.big.BigDecimalMath
import com.bagadesh.data.math.calculatePercentage
import com.bagadesh.domain.debug.customDebugValue
import com.bagadesh.domain.entities.InterestResultData
import com.bagadesh.domain.entities.OneTimeResultData
import com.bagadesh.domain.entities.base.Percentage
import com.bagadesh.domain.entities.sip.SipResultData
import com.bagadesh.domain.math.*
import com.bagadesh.domain.repository.CompoundRepository
import com.bagadesh.domain.requests.CalculateInterestRequest
import com.bagadesh.domain.result.Data
import com.bagadesh.domain.result.cagr.CAGRResult
import com.bagadesh.domain.usecases.CalculateSipResultRequest
import com.bagadesh.domain.usecases.OneTimeResultDataRequest
import com.bagadesh.domain.usecases.cagr.CalculateCAGRRequest
import java.lang.Math.pow
import java.math.BigDecimal
import java.math.MathContext
import java.math.RoundingMode
import javax.inject.Inject
import javax.inject.Singleton
import kotlin.math.pow

/**
 * Created by bagadesh on 02/08/22.
 */
@Singleton
class CompoundRepositoryImpl @Inject constructor() : CompoundRepository {

    private val hundredBigDecimal = BigDecimal.valueOf(100)

    override fun calculateSip(request: CalculateSipResultRequest): Data<SipResultData> {
        return with(request) {
            try {
                val interest = interest.toFloat()
                val year = year

                val modeledAmount = BigDecimal(periodicInvestment)
                val totalInvested = modeledAmount.multiply(BigDecimal.valueOf(year * 12.0)).toBigInteger()

                val monthlyInterestRate = (interest.toDouble().div(12)).div(100)
                val numberOfContributions = year * 12.toDouble()

                val firstInnerBracket = (1 + monthlyInterestRate).pow(numberOfContributions)

                val firstBracket = ((firstInnerBracket - 1).div(monthlyInterestRate))
                val temp = firstBracket * (1 + monthlyInterestRate)

                val result = modeledAmount.multiply(BigDecimal.valueOf(temp)).toBigInteger()
                val interestEarned = result.minus(totalInvested).toString()
                val percentageOfInterestGained = interestEarned.calculatePercentage(initial = totalInvested.toString())
                customDebugValue {
                    appendLine("calculateSip result = $result")
                    appendLine("calculateSip -> CalculateSipResultRequest = $request")
                }

                Data.Success(
                    SipResultData(
                        amount = request.periodicInvestment,
                        interest = this.interest,
                        year = this.year,
                        result = result.toString(),
                        interestEarned = result.minus(totalInvested).toString(),
                        totalInvested = totalInvested.toString(),
                        interestPercentageGained = percentageOfInterestGained
                    )
                )
            } catch (exception: Exception) {
                Data.Failure(exception = exception)
            }
        }
    }

    override fun calculateOneTime(request: OneTimeResultDataRequest): Data<OneTimeResultData> {
        return with(request) {
            try {
                customDebugValue {
                    appendTag(this@CompoundRepositoryImpl.javaClass.simpleName)
                    appendLine("OneTimeResultDataRequest : $request")
                }
                val modeledAmount = BigDecimal(amount)
                val interestRateInDecimal = (interest.toFloat() / 100f)
                val tempWithoutPrincipal = (1 + (interestRateInDecimal.div(compounded))).pow(compounded * year)
                val result = modeledAmount.multiply(BigDecimal.valueOf(tempWithoutPrincipal.toDouble()))
                val interestEarned = result.minus(modeledAmount).toBigInteger().toString()
                val interestPercentage = interestEarned.calculatePercentage(initial = modeledAmount.toBigInteger().toString())
                Data.Success(
                    OneTimeResultData(
                        amount = amount,
                        interest = interest,
                        year = year,
                        result = result.toBigInteger().toString(),
                        interestEarned = interestEarned.toBigInteger().toString(),
                        interestPercentageGained = interestPercentage
                    )
                )
            } catch (exception: Exception) {
                Data.Failure(exception = exception)
            }
        }
    }

    override fun calculateInterest(request: CalculateInterestRequest): Data<InterestResultData> {
        return try {
            val interestEarned = request.amount * request.interest
            val totalValue = interestEarned + request.amount

            Data.Success(
                InterestResultData(
                    interestEarned = interestEarned,
                    totalValue = totalValue
                )
            )
        } catch (exception: Exception) {
            Data.Failure(exception = exception)
        }
    }

    override fun calculateCAGR(request: CalculateCAGRRequest): Data<CAGRResult> {
        return try {
            val first = request.final.value.toBigDecimal().divide(request.initial.value.toBigDecimal(), 30, RoundingMode.HALF_UP)
            val result = BigDecimalMath.pow(first, BigDecimal.valueOf(1 / request.year.value), MathContext.DECIMAL32).subtract(BigDecimal(1)) * BigDecimal(100)

            //calculate absolute percentage
            val absoluteReturns = (request.final - request.initial).div(request.initial) * 100

            customDebugValue {
                appendTag(this@CompoundRepositoryImpl.javaClass.simpleName)
                appendLine("calculateCAGR : $request")
                appendLine("first : $first")
                appendLine("result : $result")
            }

            Data.Success(
                CAGRResult(
                    cagr = Percentage(result.toDouble()),
                    absolutePercentage = Percentage(absoluteReturns.toDouble())
                )
            )
        } catch (exception: Exception) {
            Data.Failure(exception = exception)
        }
    }
}