package com.bagadesh.sipcalculator.di

import android.app.Application
import android.content.Context
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

/**
 * Created by bagadesh on 05/08/22.
 */
@InstallIn(SingletonComponent::class)
@Module
class ApplicationModule {

    @Provides
    fun provideContext(application: Application): Context {
        return application
    }

}