package com.bagadesh.sipcalculator.ui.entities

import com.bagadesh.domain.entities.investment.InvestmentType
import com.bagadesh.sipcalculator.BuildConfig
import com.bagadesh.sipcalculator.debug.excludeUIs

/**
 * Created by bagadesh on 25/07/22.
 */

fun provideInvestmentTypeList() = InvestmentType.values().toList().filter {
    !excludeUIs.contains(it) || BuildConfig.DEBUG
}