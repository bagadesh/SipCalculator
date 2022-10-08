package com.bagadesh.domain.result.save

import com.bagadesh.domain.entities.investment.InvestmentType
import kotlinx.coroutines.flow.Flow

/**
 * Created by bagadesh on 02/09/22.
 */
data class SavedResults(
    val data: List<PersistenceInvestmentData>,
    val dataFlow: Flow<List<PersistenceInvestmentData>>
)

data class PersistenceInvestmentData(
    val id: String,
    val title: String,
    val investmentType: InvestmentType,
    val investmentDetails: Map<String, Any>
)

