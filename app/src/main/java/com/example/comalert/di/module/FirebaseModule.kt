package com.example.comalert.di.module

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

//@Module
//@InstallIn(SingletonComponent::class)
//object AlertModule {
//    @Provides
//    @Singleton
//    fun provideDatabase(@ApplicationContext appContext: Context): AlertDatabase {
//        return Room.databaseBuilder(
//            appContext,
//            AlertDatabase::class.java,
//            "alert_database"
//        ).build()
//    }
//
//    @Provides
//    fun provideAlertDao(database: AlertDatabase): AlertDao {
//        return database.alertDao()
//    }
//
//    @Provides
//    @Singleton
//    fun provideAlertRepository(alertDao: AlertDao): AlertRepository {
//        return AlertRepository(alertDao)
//    }
//}


@Module
@InstallIn(SingletonComponent::class)
object FirebaseModule {

    @Provides
    @Singleton
    fun provideFirebaseAuth(): FirebaseAuth {
        return FirebaseAuth.getInstance()
    }
}
