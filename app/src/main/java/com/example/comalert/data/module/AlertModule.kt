package com.example.comalert.data.module

import android.content.Context
import androidx.room.Room
import com.example.comalert.data.local.AlertDao
import com.example.comalert.data.local.AlertDatabase
import com.example.comalert.data.repository.AlertRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AlertModule {
    @Provides
    @Singleton
    fun provideDatabase(@ApplicationContext appContext: Context): AlertDatabase {
        return Room.databaseBuilder(
            appContext,
            AlertDatabase::class.java,
            "alert_database"
        ).build()
    }

    @Provides
    fun provideAlertDao(database: AlertDatabase): AlertDao {
        return database.alertDao()
    }

    @Provides
    @Singleton
    fun provideAlertRepository(alertDao: AlertDao): AlertRepository {
        return AlertRepository(alertDao)
    }
}
