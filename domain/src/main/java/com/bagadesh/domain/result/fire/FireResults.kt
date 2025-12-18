package com.bagadesh.domain.result.fire

import com.bagadesh.domain.entities.base.Currency

/**
 * Created by bagadesh on 30/08/22.
 */
data class FireResults(
    val requiredCorpus: Currency,
    val yearlyCorpusBreakdown: List<CorpusBreakdown> = emptyList()
)

data class CorpusBreakdown(
    val year: Int,
    val requiredCorpus: Currency
)
