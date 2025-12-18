package com.bagadesh.data.repository.fire

import com.bagadesh.domain.entities.base.Currency
import com.bagadesh.domain.math.realPercentage
import com.bagadesh.domain.math.times
import com.bagadesh.domain.repository.FireRepository
import com.bagadesh.domain.result.Data
import com.bagadesh.domain.result.fire.CorpusBreakdown
import com.bagadesh.domain.result.fire.FireResults
import com.bagadesh.domain.usecases.fire.CalculateFireRequest
import java.math.BigDecimal
import java.math.RoundingMode
import javax.inject.Inject
import kotlin.math.pow

/**
 * Created by bagadesh on 30/08/22.
 */
class FireRepositoryImpl @Inject constructor() : FireRepository {

    override fun calculateCorpus(request: CalculateFireRequest): Data<FireResults> {
        return with(request) {
            val yearlyExpanse = monthlyExpanse * 12
            val percentage = withdrawalPercentage.realPercentage()
            val rCorpus = yearlyExpanse.divide(percentage)

            val corpusList = mutableListOf<CorpusBreakdown>()
            val currentCorpusValue = rCorpus.toPlainString().toBigDecimal()
            val inflation = inflationRate.value.toBigDecimal().divide(BigDecimal(100), 10, RoundingMode.HALF_UP)

            for (i in 1..tenure) {
                // Future Value = Present Value * (1 + rate)^n
                val factor = (BigDecimal.ONE + inflation).pow(i)
                val futureCorpus = currentCorpusValue.multiply(factor)
                
                corpusList.add(
                    CorpusBreakdown(
                        year = i,
                        requiredCorpus = Currency(futureCorpus.toPlainString())
                    )
                )
            }

            Data.Success(
                FireResults(
                    requiredCorpus = Currency(rCorpus.toPlainString()),
                    yearlyCorpusBreakdown = corpusList
                )
            )
        }
    }
}
