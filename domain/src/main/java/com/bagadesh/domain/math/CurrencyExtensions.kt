package com.bagadesh.domain.math

import com.bagadesh.domain.entities.base.Currency
import com.bagadesh.domain.entities.base.Interest
import com.bagadesh.domain.entities.base.Percentage
import com.bagadesh.domain.entities.base.TaxRate
import java.math.BigDecimal
import java.math.RoundingMode
import kotlin.math.pow

/**
 * Created by bagadesh on 05/08/22.
 */

val twelve: BigDecimal = BigDecimal.valueOf(12)
val hundredBigDecimal: BigDecimal = BigDecimal.valueOf(100)

inline fun Currency.toMonthlyChunks(): Currency {
    val item = value.toBigDecimal()
    return Currency(value = item.div(twelve).toPlainString())
}

inline operator fun Currency.plus(other: Currency): Currency {
    val first = value.toBigDecimal()
    val second = other.value.toBigDecimal()
    return Currency(first.plus(second).toPlainString())
}

inline operator fun Currency.minus(other: Currency): Currency {
    val first = value.toBigDecimal()
    val second = other.value.toBigDecimal()
    return Currency(first.minus(second).toPlainString())
}

inline operator fun Currency.times(other: Currency): Currency {
    val first = value.toBigDecimal()
    val second = other.value.toBigDecimal()
    return Currency(first.multiply(second).toPlainString())
}

inline operator fun Currency.div(other: Currency): Currency {
    val first = value.toBigDecimal()
    val second = other.value.toBigDecimal()
    return Currency(first.divide(second, 30, RoundingMode.HALF_UP).toPlainString())
}

inline fun Currency.pow(other: Double): Currency {
    val first = value.toBigDecimal()
    return Currency(first.toDouble().pow(other).toString())
}

inline operator fun Currency.times(other: Interest): Currency {
    val first = value.toBigDecimal()
    val second = other.value.toBigDecimal().div(hundredBigDecimal)
    return Currency(first.multiply(second).toPlainString())
}

inline operator fun Currency.times(other: TaxRate): Currency {
    val first = value.toBigDecimal()
    val second = other.value.toBigDecimal().div(hundredBigDecimal)
    return Currency(first.multiply(second).toPlainString())
}

inline operator fun Currency.times(other: Int): BigDecimal {
    val first = value.toBigDecimal()
    val second = other.toBigDecimal()
    return first.multiply(second)
}


//Percentage
fun Percentage.realPercentage(): BigDecimal {
    return value.toBigDecimal().divide(hundredBigDecimal, 2, RoundingMode.HALF_UP)
}
