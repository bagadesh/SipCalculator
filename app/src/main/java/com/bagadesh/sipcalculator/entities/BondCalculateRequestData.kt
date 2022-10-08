package com.bagadesh.sipcalculator.entities

import com.bagadesh.domain.entities.base.Currency
import com.bagadesh.domain.entities.base.Interest
import com.bagadesh.domain.entities.base.TaxRate

data class BondCalculateRequestData(
    val investment: Currency,
    val bondRate: Interest,
    val taxSlabRate: TaxRate
)