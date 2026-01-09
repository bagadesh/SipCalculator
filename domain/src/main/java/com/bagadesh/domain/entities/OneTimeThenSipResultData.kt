package com.bagadesh.domain.entities

import com.bagadesh.domain.entities.sip.SipResultData

/**
 * Created by bagadesh.
 */
data class OneTimeThenSipResultData(
    val sipResultData: SipResultData,
    val oneTimeResultData: OneTimeResultData,
    val totalResult: String
)

