package com.example.comalert.data.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.comalert.data.remote.Alert

//@Database(entities = [Alert::class], version = 1)
//abstract class AlertDatabase : RoomDatabase() {
//    abstract fun alertDaoRoom(): AlertDaoRoom
//
//    companion object {
//        @Volatile
//        private var INSTANCE: AlertDatabase? = null
//
//        fun getDatabase(context: Context): AlertDatabase {
//            return INSTANCE ?: synchronized(this) {
//                val instance = Room.databaseBuilder(
//                    context.applicationContext,
//                    AlertDatabase::class.java,
//                    "alert_database"
//                ).build()
//                INSTANCE = instance
//                instance
//            }
//        }
//    }
//}
