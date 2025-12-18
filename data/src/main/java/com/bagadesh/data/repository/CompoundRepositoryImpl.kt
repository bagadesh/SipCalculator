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

    override fun calculateHomeLoanEMI(request: com.bagadesh.domain.requests.HomeLoanEMIRequest): Data<com.bagadesh.domain.entities.HomeLoanEMIResultData> {
        return try {
            val principal = request.principal
            val rate = request.interestRate / 12 / 100
            val tenureMonths = request.tenureYears * 12

            val emi = if (rate != 0.0) {
                (principal * rate * (1 + rate).pow(tenureMonths)) / ((1 + rate).pow(tenureMonths) - 1)
            } else {
                principal / tenureMonths
            }

            val totalPayment = emi * tenureMonths
            val totalInterest = totalPayment - principal

            // Inflation adjusted EMI value
            // PV = EMI / (1 + inflation/100)^inflationYears
            val inflationAdjustedEMI = emi / (1 + request.inflationRate / 100).pow(request.inflationYears)

            // Calculate Yearly Rent Breakdown
            val rentBreakdownList = mutableListOf<com.bagadesh.domain.entities.RentBreakdown>()
            var currentMonthlyRent = request.currentRent
            
            for (year in 1..request.tenureYears) {
                // Rent increases every year
                if (year > 1) {
                    currentMonthlyRent *= (1 + request.rentIncreaseRate / 100)
                }
                val yearlyTotal = currentMonthlyRent * 12
                rentBreakdownList.add(
                    com.bagadesh.domain.entities.RentBreakdown(
                        year = year,
                        monthlyRent = currentMonthlyRent,
                        yearlyTotal = yearlyTotal
                    )
                )
            }

            // Calculate Yearly Inflation Breakdown
            val inflationBreakdownList = mutableListOf<com.bagadesh.domain.entities.InflationBreakdown>()
            for (year in 1..request.tenureYears) {
                // Inflation adjusted EMI decreases every year in terms of purchasing power
                // PV = EMI / (1 + inflation/100)^year
                val adjustedEmi = emi / (1 + request.inflationRate / 100).pow(year)
                inflationBreakdownList.add(
                    com.bagadesh.domain.entities.InflationBreakdown(
                        year = year,
                        inflationAdjustedEmi = adjustedEmi
                    )
                )
            }

            // Calculate Yearly SIP Breakdown
            val sipBreakdownList = mutableListOf<com.bagadesh.domain.entities.SipBreakdown>()
            var accumulatedCorpus = 0.0
            val monthlySipInterestRate = request.sipInterestRate / 12 / 100
            
            var currentRentForSip = request.currentRent
            
            for (year in 1..request.tenureYears) {
                // Determine rent for this year (starts increasing from year 2)
                if (year > 1) {
                    currentRentForSip *= (1 + request.rentIncreaseRate / 100)
                }
                
                // 1. Grow previous corpus for 1 year (12 months)
                if (accumulatedCorpus > 0) {
                    accumulatedCorpus *= (1 + monthlySipInterestRate).pow(12)
                }
                
                // 2. Add new investments if possible
                val investableAmountMonthly = emi - currentRentForSip
                var yearlyInvestment = 0.0
                
                if (investableAmountMonthly > 0) {
                    yearlyInvestment = investableAmountMonthly * 12
                    
                    // Future Value of monthly SIP for 12 months
                    val fvFactor = (1 + monthlySipInterestRate).pow(12) - 1
                    val sipValueForYear = (investableAmountMonthly * fvFactor * (1 + monthlySipInterestRate)) / monthlySipInterestRate
                    accumulatedCorpus += sipValueForYear
                }
                
                sipBreakdownList.add(
                    com.bagadesh.domain.entities.SipBreakdown(
                        year = year,
                        investableAmountMonthly = if (investableAmountMonthly > 0) investableAmountMonthly else 0.0,
                        totalInvested = yearlyInvestment, 
                        projectedValue = accumulatedCorpus
                    )
                )
            }

            Data.Success(
                com.bagadesh.domain.entities.HomeLoanEMIResultData(
                    emi = String.format("%.0f", emi),
                    totalInterest = String.format("%.0f", totalInterest),
                    totalPayment = String.format("%.0f", totalPayment),
                    inflationAdjustedEMI = String.format("%.0f", inflationAdjustedEMI),
                    yearlyRentBreakdown = rentBreakdownList,
                    yearlyInflationBreakdown = inflationBreakdownList,
                    yearlySipBreakdown = sipBreakdownList
                )
            )
        } catch (exception: Exception) {
            Data.Failure(exception = exception)
        }
    }
}