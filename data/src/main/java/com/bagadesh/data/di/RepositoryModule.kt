package com.bagadesh.data.di

import com.bagadesh.data.repository.CompoundRepositoryImpl
import com.bagadesh.data.repository.FeatureFlagRepositoryImpl
import com.bagadesh.data.repository.IncomeRepositoryImpl
import com.bagadesh.data.repository.TaxRepositoryImpl
import com.bagadesh.data.repository.fire.FireRepositoryImpl
import com.bagadesh.data.repository.save.PersistenceRepositoryImpl
import com.bagadesh.domain.repository.*
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

/**
 * Created by bagadesh on 02/08/22.
 */
@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {

    @Binds
    abstract fun bindCompoundRepositoryImpl(compoundRepositoryImpl: CompoundRepositoryImpl): CompoundRepository

    @Binds
    abstract fun bindTaxRepositoryImpl(taxRepositoryImpl: TaxRepositoryImpl): TaxRepository

    @Binds
    abstract fun bindIncomeRepositoryImpl(incomeRepositoryImpl: IncomeRepositoryImpl): IncomeRepository

    @Binds
    abstract fun bindFeatureFlagRepositoryImpl(featureFlagRepositoryImpl: FeatureFlagRepositoryImpl): FeatureFlagRepository

    @Binds
    abstract fun bindPersistenceRepositoryImpl(persistenceRepositoryImpl: PersistenceRepositoryImpl): PersistenceRepository

    @Binds
    abstract fun bindFireRepositoryImpl(fireRepositoryImpl: FireRepositoryImpl): FireRepository
}