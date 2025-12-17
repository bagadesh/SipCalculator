package com.bagadesh.data.compound

import com.bagadesh.data.repository.CompoundRepositoryImpl
import com.bagadesh.domain.requests.HomeLoanEMIRequest
import com.bagadesh.domain.result.Data
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Test

class HomeLoanEMICalculationTest {

    private val repository = CompoundRepositoryImpl()

    @Test
    fun `test home loan emi calculation`() {
        // Given
        val request = HomeLoanEMIRequest(
            principal = 100000.0,
            interestRate = 10.0,
            tenureYears = 1,
            inflationRate = 6.0,
            inflationYears = 5
        )

        // When
        val result = repository.calculateHomeLoanEMI(request)

        // Then
        assertTrue(result is Data.Success)
        val data = (result as Data.Success).data
        
        // Manual calc:
        // P = 100000, r = 10/12/100 = 0.008333, n = 12
        // EMI = 100000 * 0.008333 * (1.008333)^12 / ((1.008333)^12 - 1)
        // EMI ≈ 8791.59 -> 8792
        
        assertEquals("8792", data.emi)
        
        // Total Payment = 8791.588... * 12 = 105499.06... -> 105499
        assertEquals("105499", data.totalPayment)
        
        // Total Interest = 105499 - 100000 = 5499
        assertEquals("5499", data.totalInterest)

        // Inflation Adjusted EMI
        // PV = 8791.59 / (1 + 0.06)^5 = 8791.59 / 1.3382 = 6569.7 -> 6570
        assertEquals("6570", data.inflationAdjustedEMI)
    }
}

