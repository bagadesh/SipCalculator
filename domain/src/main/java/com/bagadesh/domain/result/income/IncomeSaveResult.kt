package com.bagadesh.domain.result.income

import com.bagadesh.domain.entities.base.Currency
import com.bagadesh.domain.entities.base.Interest
import com.bagadesh.domain.entities.base.Percentage
import com.bagadesh.domain.entities.base.Year

/**
 * Created by bagadesh on 17/08/22.
 */
data class IncomeSaveResult(
    // Request details
    val targetedAmount: Currency,
    val expectedCAGR: Interest,
    val targetYear: Year,

    // Response details
    val absoluteMonthlyIncomeNeedToSave: Currency,
    val percentageMonthlyIncomeNeedToSave: Percentage
)
