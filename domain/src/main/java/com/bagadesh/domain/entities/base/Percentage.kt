package com.bagadesh.domain.entities.base

import java.math.RoundingMode

/**
 * Created by bagadesh on 17/08/22.
 */
@JvmInline
value class Percentage(val value: Double) {
    val percentageForDisplay: Double
        get() {
            return value.toBigDecimal().setScale(2, RoundingMode.HALF_UP).toDouble()
        }


}