package com.bagadesh.domain.result.income

import com.bagadesh.domain.entities.base.Currency

/**
 * Created by bagadesh on 07/08/22.
 */
data class IncomeSourceData(
    val listOfIncomeSources: List<IncomeSource>
)


data class IncomeSource(
    val person: Person,
    val income: Currency
)

@JvmInline
value class Person(val value: String)

