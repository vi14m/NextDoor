package com.example.comalert.data.remote

import com.google.firebase.firestore.DocumentId

data class Alert(
    @DocumentId val id: String = "",
    val title: String = "",
    val description: String = "",
    val urgencyLevel: String = "",
    val timestamp: Long = System.currentTimeMillis(),
    val mediaUri: String? = null,
    val latitude: Double = 0.0,
    val longitude: Double = 0.0
)




