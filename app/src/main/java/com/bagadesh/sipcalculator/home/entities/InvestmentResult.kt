package com.bagadesh.sipcalculator.home.entities

import com.bagadesh.domain.constants.InvestmentDetailConstants
import com.bagadesh.domain.entities.OneTimeResultData
import com.bagadesh.domain.entities.SipThenOneTimeResultData
import com.bagadesh.domain.entities.sip.SipResultData


/**
 * Created by bagadesh on 28/07/22.
 */
sealed class InvestmentResult {

    data class SipResult(val sipResultData: SipResultData) : InvestmentResult()

    data class OneTimeResult(val oneTimeResultData: OneTimeResultData) : InvestmentResult()

    data class SipThenOneTime(val sipThenOneTimeResultData: SipThenOneTimeResultData) : InvestmentResult()
}


