package com.example.comalert.data.local

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.example.comalert.data.remote.Alert

//@Dao
//interface AlertDaoRoom {
//    @Insert
//    suspend fun insertAlertRoom(alert: AlertRoom)
//
//    @Query("SELECT * FROM alertsRoom ORDER BY timestamp DESC")
//    fun getAllAlertsRoom(): LiveData<List<AlertRoom>>
//
//    @Query("SELECT * FROM alertsRoom WHERE id = :alertId")
//    fun getAlertByIdRoom(alertId: Int): LiveData<AlertRoom>
//}