package com.bagadesh.sipcalculator.savedResults.entities

import com.bagadesh.domain.result.save.PersistenceInvestmentData

/**
 * Created by bagadesh on 02/09/22.
 */
data class SavedResultsUIData(
    val data: List<PersistenceInvestmentData>,
)
