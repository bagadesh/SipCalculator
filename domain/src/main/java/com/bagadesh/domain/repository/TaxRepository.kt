package com.bagadesh.domain.repository

import com.bagadesh.domain.entities.TaxResultData
import com.bagadesh.domain.entities.base.Currency
import com.bagadesh.domain.entities.base.TaxRate
import com.bagadesh.domain.result.Data

/**
 * Created by bagadesh on 05/08/22.
 */
interface TaxRepository {

    fun calculateTax(amount: Currency, taxRate: TaxRate): Data<TaxResultData>

}