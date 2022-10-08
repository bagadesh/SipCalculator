package com.bagadesh.data.repository.save

import com.bagadesh.data.mapper.InvestmentsMapper
import com.bagadesh.data.persistence.dao.InvestmentsDao
import com.bagadesh.data.persistence.entities.Investments
import com.bagadesh.domain.debug.customDebugValue
import com.bagadesh.domain.entities.investment.InvestmentType
import com.bagadesh.domain.repository.PersistenceRepository
import com.bagadesh.domain.result.Data
import com.bagadesh.domain.result.save.PersistenceInvestmentData
import com.bagadesh.domain.result.save.SaveResults
import com.bagadesh.domain.result.save.SavedResults
import com.bagadesh.domain.usecases.save.GetDeleteResultUseCaseParam
import com.bagadesh.domain.usecases.save.GetSaveResultsUseCaseRequest
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

/**
 * Created by bagadesh on 27/08/22.
 */
class PersistenceRepositoryImpl @Inject constructor(
    private val investmentsDao: InvestmentsDao,
    private val investmentsMapper: InvestmentsMapper,
) : PersistenceRepository {

    override fun saveResults(request: GetSaveResultsUseCaseRequest): Data<SaveResults> {
        return with(request) {
            try {
                investmentsDao.insertAll(
                    Investments(
                        investmentType = investmentType.displayValue,
                        investmentDetails = investmentDetails,
                        title = title
                    )
                )
                Data.Success(SaveResults())
            } catch (exception: Exception) {
                Data.Failure(exception = exception)
            }
        }
    }

    override fun getAllSavedResults(): Data<SavedResults> {
        return Data.Success(
            SavedResults(
                data = investmentsDao.getAllInvestments().map {
                    investmentsMapper.mapFrom(it)
                },
                dataFlow = investmentsDao.getAllInvestmentsFlow().map {
                    it.map { investments ->
                        investmentsMapper.mapFrom(investments)
                    }
                }
            )
        )
    }

    override fun getAllSavedResultsFlow(): Flow<Data<List<PersistenceInvestmentData>>> {
        return investmentsDao.getAllInvestmentsFlow().map {
            Data.Success(
                data = it.map { investments ->
                    investmentsMapper.mapFrom(investments)
                }
            )
        }
    }

    override fun deleteInvestment(request: GetDeleteResultUseCaseParam): Data<Unit> {
        val affectedRows = investmentsDao.deleteInvestmentsById(investmentId = request.investmentId)
        customDebugValue {
            appendLine("deleteInvestment affectedRows = $affectedRows")
        }
        return Data.Success(Unit)
    }
}