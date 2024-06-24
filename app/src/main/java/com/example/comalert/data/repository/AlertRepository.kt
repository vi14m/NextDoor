package com.example.comalert.data.repository

import androidx.lifecycle.LiveData
import com.example.comalert.data.local.Alert
import com.example.comalert.data.local.AlertDao

class AlertRepository(private val alertDao: AlertDao) {
    val recentAlerts: LiveData<List<Alert>> = alertDao.getAllAlerts()

    fun getAlertById(id: Int): LiveData<Alert> {
        return alertDao.getAlertById(id)
    }
}
