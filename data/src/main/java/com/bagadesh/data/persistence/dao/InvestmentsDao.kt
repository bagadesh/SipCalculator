package com.bagadesh.data.persistence.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.bagadesh.data.persistence.entities.Investments
import kotlinx.coroutines.flow.Flow

/**
 * Created by bagadesh on 02/09/22.
 */
@Dao
interface InvestmentsDao {

    @Query("SELECT * FROM investments")
    fun getAllInvestments(): List<Investments>

    @Query("SELECT * FROM investments")
    fun getAllInvestmentsFlow(): Flow<List<Investments>>

    @Insert
    fun insertAll(vararg investments: Investments)

    @Delete
    fun deleteInvestments(investments: Investments)

    @Query("DELETE FROM investments WHERE id = :investmentId")
    fun deleteInvestmentsById(investmentId: Int): Int


}