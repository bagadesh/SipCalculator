package com.bagadesh.data.di

import com.bagadesh.data.persistence.AppDatabase
import com.bagadesh.data.persistence.dao.InvestmentsDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

/**
 * Created by bagadesh on 02/09/22.
 */

@InstallIn(SingletonComponent::class)
@Module
class DaoModule {

    @Singleton
    @Provides
    fun bindInvestmentDao(appDatabase: AppDatabase): InvestmentsDao {
        return appDatabase.investmentsDao()
    }

}