package com.bagadesh.domain.entities

import com.bagadesh.domain.entities.base.Currency

/**
 * Created by bagadesh on 05/08/22.
 */
data class TaxResultData(
    val taxedAmount: Currency,
    val remainingAmount: Currency
)
