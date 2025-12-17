package com.bagadesh.domain.repository

import com.bagadesh.domain.entities.InterestResultData
import com.bagadesh.domain.entities.OneTimeResultData
import com.bagadesh.domain.entities.sip.SipResultData
import com.bagadesh.domain.requests.CalculateInterestRequest
import com.bagadesh.domain.result.Data
import com.bagadesh.domain.result.cagr.CAGRResult
import com.bagadesh.domain.usecases.CalculateSipResultRequest
import com.bagadesh.domain.usecases.OneTimeResultDataRequest
import com.bagadesh.domain.usecases.cagr.CalculateCAGRRequest

import com.bagadesh.domain.entities.HomeLoanEMIResultData
import com.bagadesh.domain.requests.HomeLoanEMIRequest

/**
 * Created by bagadesh on 02/08/22.
 */
interface CompoundRepository {

    fun calculateSip(request: CalculateSipResultRequest): Data<SipResultData>

    fun calculateOneTime(request: OneTimeResultDataRequest): Data<OneTimeResultData>

    fun calculateInterest(request: CalculateInterestRequest): Data<InterestResultData>

    fun calculateCAGR(request: CalculateCAGRRequest): Data<CAGRResult>

    fun calculateHomeLoanEMI(request: HomeLoanEMIRequest): Data<HomeLoanEMIResultData>

}