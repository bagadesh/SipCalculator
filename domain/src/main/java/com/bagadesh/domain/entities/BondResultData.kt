package com.bagadesh.domain.entities

import com.bagadesh.domain.entities.base.Currency
import com.bagadesh.domain.entities.base.Interest
import com.bagadesh.domain.entities.base.TaxRate

/**
 * Created by bagadesh on 05/08/22.
 */
data class BondResultData(
    val amount: Currency,
    val bondRate: Interest,
    val taxRate: TaxRate,
    val monthlyPreTaxInterest: Currency,
    val monthlyPostTaxInterest: Currency,
)
