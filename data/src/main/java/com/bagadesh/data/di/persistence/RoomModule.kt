package com.bagadesh.data.di.persistence

import android.content.Context
import androidx.room.Room
import com.bagadesh.data.persistence.AppDatabase
import com.bagadesh.data.persistence.databaseName
import com.bagadesh.data.persistence.typeConverters.MapTypeConverter
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
class RoomModule {

    @Provides
    @Singleton
    fun provideAppDatabase(
        applicationContext: Context,
        mapTypeConverter: MapTypeConverter
    ): AppDatabase {
        return Room.databaseBuilder(
            applicationContext,
            AppDatabase::class.java, databaseName
        )
            .addTypeConverter(mapTypeConverter)
            .build()
    }

}