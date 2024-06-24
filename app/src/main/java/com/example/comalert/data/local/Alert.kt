package com.example.comalert.data.local

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "alerts")
data class Alert(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val title: String,
    val description: String,
    val urgencyLevel: String,
    val timestamp: Long,
    val mediaUri: String? = null
)



