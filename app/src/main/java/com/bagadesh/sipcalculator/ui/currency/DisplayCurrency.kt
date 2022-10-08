package com.bagadesh.sipcalculator.ui.currency

import java.util.*

/**
 * Created by bagadesh on 25/07/22.
 */
object DisplayCurrency {

    private val currencySymbol: String
        get() {
            val current: Locale = Locale.getDefault()
            return Currency.getInstance(current).symbol
        }


    fun display(value: String): String {
        return "$currencySymbol $value"
    }

    fun currentCurrency(): String {
        return currencySymbol
    }

}