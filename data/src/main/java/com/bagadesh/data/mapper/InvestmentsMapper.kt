package com.bagadesh.data.mapper

import com.bagadesh.data.persistence.entities.Investments
import com.bagadesh.domain.entities.investment.InvestmentType
import com.bagadesh.domain.mapper.Mapper
import com.bagadesh.domain.result.save.PersistenceInvestmentData
import javax.inject.Inject

/**
 * Created by bagadesh on 21/09/22.
 */
class InvestmentsMapper @Inject constructor() : Mapper<Investments, PersistenceInvestmentData> {

    override fun mapFrom(data: Investments): PersistenceInvestmentData {
        return PersistenceInvestmentData(
            id = data.id.toString(),
            title = data.title,
            investmentType = InvestmentType.mapStringData(value = data.investmentType),
            investmentDetails = data.investmentDetails
        )
    }

    override fun mapTo(data: PersistenceInvestmentData): Investments {
        TODO("Not yet implemented")
    }
}