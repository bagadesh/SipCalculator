package com.bagadesh.sipcalculator.home.ui.compoundFrequency

/**
 * Created by bagadesh on 25/07/22.
 */
enum class CompoundFrequency(val displayValue: String, val  numValue: Int) {
    QUARTERLY("Quarterly", 4),
    HALF_YEARLY("Half Yearly", 2),
    YEARLY("Yearly", 1)
}