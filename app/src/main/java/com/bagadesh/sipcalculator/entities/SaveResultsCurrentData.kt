package com.bagadesh.sipcalculator.entities

import com.bagadesh.domain.entities.investment.InvestmentType

/**
 * Created by bagadesh on 02/09/22.
 */
sealed class SaveResultsCurrentData {

    object Empty : SaveResultsCurrentData()

    data class Success(val investmentType: InvestmentType, val investDetails: Map<String, Any>) : SaveResultsCurrentData()
}