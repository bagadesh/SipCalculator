package com.bagadesh.data.persistence

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.bagadesh.data.persistence.dao.InvestmentsDao
import com.bagadesh.data.persistence.entities.Investments
import com.bagadesh.data.persistence.typeConverters.MapTypeConverter

/**
 * Created by bagadesh on 02/09/22.
 */

const val databaseName = "sipCalculationDatabase"
@TypeConverters(value = [MapTypeConverter::class])
@Database(entities = [Investments::class], version = 1)
abstract class AppDatabase: RoomDatabase() {

    abstract fun investmentsDao(): InvestmentsDao

}