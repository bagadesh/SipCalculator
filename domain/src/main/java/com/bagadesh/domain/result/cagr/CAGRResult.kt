package com.bagadesh.domain.result.cagr

import com.bagadesh.domain.entities.base.Percentage

/**
 * Created by bagadesh on 21/08/22.
 */
data class CAGRResult(
    val cagr: Percentage,
    val absolutePercentage: Percentage
)
