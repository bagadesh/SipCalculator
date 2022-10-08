package com.bagadesh.sipcalculator.home.entities

import com.bagadesh.domain.entities.investment.InvestmentType
import com.bagadesh.sipcalculator.home.ui.compoundFrequency.CompoundFrequency

/**
 * Created by bagadesh on 25/07/22.
 */
data class CalculateInvestmentData(
    val amount: String,
    val interest: Int,
    val year: Int,
    val compoundFrequency: CompoundFrequency,
    val investmentType: InvestmentType,

    val interestForSipThenOnTime: Int,
    val yearForSipThenOnTime: Int,
)
