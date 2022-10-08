package com.bagadesh.data.math

import java.math.BigInteger

/**
 * Created by bagadesh on 05/08/22.
 */
private val hundredBigInteger = BigInteger.valueOf(100L)

fun String.calculatePercentage(initial: String): String {
    val gainBig = toBigInteger()
    val initialBig = initial.toBigInteger()
    return gainBig.multiply(hundredBigInteger).div(initialBig).toString()
}

