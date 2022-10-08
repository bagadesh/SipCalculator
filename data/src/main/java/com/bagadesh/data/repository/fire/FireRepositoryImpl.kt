package com.bagadesh.data.repository.fire

import com.bagadesh.domain.debug.customDebugValue
import com.bagadesh.domain.entities.base.Currency
import com.bagadesh.domain.math.hundredBigDecimal
import com.bagadesh.domain.math.realPercentage
import com.bagadesh.domain.math.times
import com.bagadesh.domain.repository.FireRepository
import com.bagadesh.domain.result.Data
import com.bagadesh.domain.result.fire.FireResults
import com.bagadesh.domain.usecases.fire.CalculateFireRequest
import javax.inject.Inject

/**
 * Created by bagadesh on 30/08/22.
 */
class FireRepositoryImpl @Inject constructor() : FireRepository {

    override fun calculateCorpus(request: CalculateFireRequest): Data<FireResults> {
        return with(request) {
            val yearlyExpanse = monthlyExpanse * 12
            val percentage = withdrawalPercentage.realPercentage()
            val rCorpus = yearlyExpanse.divide(percentage)
            Data.Success(FireResults(requiredCorpus = Currency(rCorpus.toPlainString())))
        }
    }
}