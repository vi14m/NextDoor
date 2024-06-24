package com.example.comalert.data.local

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query

@Dao
interface AlertDao {
    @Insert
    suspend fun insertAlert(alert: Alert)

    @Query("SELECT * FROM alerts ORDER BY timestamp DESC")
    fun getAllAlerts(): LiveData<List<Alert>>

    @Query("SELECT * FROM alerts WHERE id = :alertId")
    fun getAlertById(alertId: Int): LiveData<Alert>
}