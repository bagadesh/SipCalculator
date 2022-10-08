package com.bagadesh.sipcalculator.debug

import com.bagadesh.domain.entities.investment.InvestmentType

/**
 * Created by bagadesh on 23/07/22.
 */

const val GLOBAL_ENABLE_DEBUG = true
const val ENABLE_V2 = true

val excludeUIs = listOf(
    InvestmentType.EXPERIMENT
)


const val SHOW_COLOR_BAR = true

const val SHOW_COMPOUND_INTEREST_DETAILS = true

inline fun debugValue(action: StringBuilder.() -> Unit) {
    if (GLOBAL_ENABLE_DEBUG) {
        val value = buildString(action)
        println(value)
    }
}

inline fun customDebugValue(action: CustomStringBuilder.() -> Unit) {
    if (GLOBAL_ENABLE_DEBUG) {
        val value = buildCustomString(action)
        println(value)
    }
}


