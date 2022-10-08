package com.bagadesh.sipcalculator.math

import java.math.BigDecimal
import java.text.NumberFormat
import java.util.*

/**
 * Created by bagadesh on 25/07/22.
 */
object SmartMoneyRepresent {

    private val list = listOf("k", "lakh", "Cr")
    private val numberFormatInstance = NumberFormat.getNumberInstance(Locale("en", "IN"))

    fun makeItPretty(value: String): String {
        val len = value.lengthBeforeBot()
        return when {
            len <= 3 -> {
                value
            }
            else -> {
                buildString {
                    append(value.provideShortedCurrency(len))
                    append(" ")
                    append((len - 1).countToCurrencyFormat())
                }
            }
        }
    }

    fun makeItIntegerWithoutDecimal(value: String): String {
        val valueBigInteger = BigDecimal(value)
        return numberFormatInstance.format(valueBigInteger)
    }

    private fun String.lengthBeforeBot(): Int {
        if (length == 0) {
            return 0
        }
        val stringBig = toBigDecimal().toBigInteger()
        return stringBig.toString().length
    }


    private fun Int.countToCurrencyFormat(): String {
        return when (this) {
            3, 4 -> list[0]
            5, 6 -> list[1]
            else -> list[2]
        }
    }

    private fun String.provideShortedCurrency(len: Int): String {
        return when (len - 1) {
            3, 5, 7 -> {
                if (get(1) == '0') {
                    "${get(0)}"
                } else {
                    "${get(0)}.${get(1)}"
                }
            }
            4, 6 -> {
                substring(0, 2)
            }
            else -> {
                // 10 Cr and more
                substring(0, len - 7)
            }
        }
    }

}