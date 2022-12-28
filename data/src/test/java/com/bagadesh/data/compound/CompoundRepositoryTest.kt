package com.bagadesh.data.compound

import com.bagadesh.data.extensions.expectAssert
import com.bagadesh.data.repository.CompoundRepositoryImpl
import com.bagadesh.domain.entities.OneTimeResultData
import com.bagadesh.domain.entities.base.Currency
import com.bagadesh.domain.entities.base.Percentage
import com.bagadesh.domain.entities.base.Year
import com.bagadesh.domain.entities.sip.SipResultData
import com.bagadesh.domain.repository.CompoundRepository
import com.bagadesh.domain.result.Data
import com.bagadesh.domain.result.cagr.CAGRResult
import com.bagadesh.domain.usecases.CalculateSipResultRequest
import com.bagadesh.domain.usecases.OneTimeResultDataRequest
import com.bagadesh.domain.usecases.cagr.CalculateCAGRRequest
import org.junit.jupiter.api.Test

/**
 * Created by bagadesh on 27/12/22.
 */
class CompoundRepositoryTest {


    private val compoundRepository: CompoundRepository = CompoundRepositoryImpl()

    @Test
    fun `whenever one time received proper values expect Success`() {
        val request = OneTimeResultDataRequest(
            amount = "1000",
            interest = "10",
            year = 2,
            compounded = 1
        )
        val expectedResult = Data.Success(
            OneTimeResultData(
                amount = request.amount,
                interest = request.interest,
                year = request.year,
                result = "1210",
                interestEarned = "210",
                interestPercentageGained = "21"
            )
        )
        val result = compoundRepository.calculateOneTime(request = request)
        result.expectAssert(expectedResult)
    }

    @Test
    fun `whenever sip receives proper values expect Success`() {
        val request = CalculateSipResultRequest(
            periodicInvestment = "1000",
            interest = "12",
            year = 10,
        )
        val expectedResult = Data.Success(
            SipResultData(
                amount = request.periodicInvestment,
                interest = request.interest,
                year = request.year,
                result = "232339",
                interestEarned = "112339",
                interestPercentageGained = "93",
                totalInvested = "120000"
            )
        )
        val result = compoundRepository.calculateSip(request = request)
        result.expectAssert(expectedResult)
    }

    @Test
    fun `whenever CARG receives proper values expect Success`() {
        val request = CalculateCAGRRequest(
            initial = Currency("1000"),
            final = Currency("2000"),
            year = Year(10.0),
        )
        val expectedResult = Data.Success(
            CAGRResult(
                cagr = Percentage(7.1773),
                absolutePercentage = Percentage(100.0),
            )
        )
        val result = compoundRepository.calculateCAGR(request = request)
        result.expectAssert(expectedResult)
    }

}