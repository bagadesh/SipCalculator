package com.bagadesh.domain.entities

import com.bagadesh.domain.entities.sip.SipResultData

/**
 * Created by bagadesh on 05/08/22.
 */
data class SipThenOneTimeResultData(
    val sipResultData: SipResultData,
    val oneTimeResultData: OneTimeResultData
)
