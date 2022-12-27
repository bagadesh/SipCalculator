package com.bagadesh.data.tax

import com.bagadesh.data.repository.TaxRepositoryImpl
import com.bagadesh.domain.entities.TaxResultData
import com.bagadesh.domain.entities.base.Currency
import com.bagadesh.domain.entities.base.TaxRate
import com.bagadesh.domain.repository.TaxRepository
import com.bagadesh.domain.result.Data
import org.junit.jupiter.api.Test

/**
 * Created by bagadesh on 27/12/22.
 */
class TaxRepositoryTest {

    private var taxRepository: TaxRepository = TaxRepositoryImpl()

//    @Test
//    fun calculateTaxTest() {
//        val givenAmount = Currency("1000")
//        val givenTaxRate = TaxRate(5.0)
//        val expectResult = TaxResultData(taxedAmount = Currency("100.0"), remainingAmount = Currency("900.0"))
//
//        val result = taxRepository.calculateTax(amount = givenAmount, taxRate = givenTaxRate)
//        println("$result")
//        assert(result is Data.Success)
//        assert((result as Data.Success).data == expectResult)
//    }

}