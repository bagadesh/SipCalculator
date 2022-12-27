package com.bagadesh.data.compound

import com.bagadesh.data.extensions.expect
import com.bagadesh.data.repository.CompoundRepositoryImpl
import com.bagadesh.domain.entities.OneTimeResultData
import com.bagadesh.domain.repository.CompoundRepository
import com.bagadesh.domain.result.Data
import com.bagadesh.domain.usecases.OneTimeResultDataRequest
import org.junit.jupiter.api.Test

/**
 * Created by bagadesh on 27/12/22.
 */
class CompoundRepositoryTest {


    private val compoundRepository: CompoundRepository = CompoundRepositoryImpl()

    @Test
    fun testOneTime() {
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
        assert(result.expect(expectedResult))
    }

}