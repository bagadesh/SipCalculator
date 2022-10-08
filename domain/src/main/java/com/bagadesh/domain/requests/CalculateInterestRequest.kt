package com.bagadesh.domain.requests

import com.bagadesh.domain.entities.base.Currency
import com.bagadesh.domain.entities.base.Interest

/**
 * Created by bagadesh on 05/08/22.
 */
data class CalculateInterestRequest(
    val amount: Currency,
    val interest: Interest,
)
