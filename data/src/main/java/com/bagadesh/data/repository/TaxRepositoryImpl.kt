package com.bagadesh.data.repository

import com.bagadesh.domain.entities.TaxResultData
import com.bagadesh.domain.entities.base.Currency
import com.bagadesh.domain.entities.base.TaxRate
import com.bagadesh.domain.math.minus
import com.bagadesh.domain.math.times
import com.bagadesh.domain.repository.TaxRepository
import com.bagadesh.domain.result.Data
import javax.inject.Inject

/**
 * Created by bagadesh on 05/08/22.
 */
class TaxRepositoryImpl @Inject constructor(): TaxRepository {

    override fun calculateTax(amount: Currency, taxRate: TaxRate): Data<TaxResultData> {
        return try {
            val taxedAmount = amount * taxRate
            val remainingAmount = amount - taxedAmount
            Data.Success(
                TaxResultData(
                    taxedAmount = taxedAmount,
                    remainingAmount = remainingAmount
                )
            )
        } catch (exception: Exception) {
            Data.Failure(exception = exception)
        }
    }
}