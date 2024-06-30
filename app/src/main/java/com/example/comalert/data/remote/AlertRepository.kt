package com.example.comalert.data.remote

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.firestore.toObject
import com.google.firebase.firestore.toObjects
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.tasks.await
import javax.inject.Inject
import javax.inject.Singleton

//class AlertRepository(private val alertDao: AlertDao) {
//    val recentAlerts: LiveData<List<Alert>> = alertDao.getAllAlerts()
//
//    fun getAlertById(id: Int): LiveData<Alert> {
//        return alertDao.getAlertById(id)
//    }
//}



@Singleton
class AlertRepository @Inject constructor() {
    private val firestore = Firebase.firestore
    private val _recentAlerts = MutableLiveData<List<Alert>>()
    val recentAlerts: LiveData<List<Alert>> get() = _recentAlerts

    init {
        fetchRecentAlerts()
    }

    private fun fetchRecentAlerts() {
        firestore.collection("alerts")
            .orderBy("timestamp",com.google.firebase.firestore.Query.Direction.DESCENDING)
            .addSnapshotListener { snapshot, e ->
                if (e != null || snapshot == null) {
                    return@addSnapshotListener
                }
                if (!snapshot.isEmpty) {
                    val alerts = snapshot.  toObjects<Alert>()
                    _recentAlerts.postValue(alerts)
                }

            }
    }

    suspend fun getAlertById(id: String): Alert? {
        return try {
            val document = firestore.collection("alerts").document(id).get().await()
            document.toObject(Alert::class.java)
        } catch (e: Exception) {
            Log.d("getAlertError", "getAlertById: ${e.message}")
            null
        }
    }

    suspend fun insertAlert(alert: Alert) {
        try {
            firestore.collection("alerts").add(alert).await()
            Log.d("insertSuccess", "Alert successfully added!")
        } catch (e: Exception) {
            Log.d("insertError", "insertAlert: ${e.message}")
        }
    }
}


