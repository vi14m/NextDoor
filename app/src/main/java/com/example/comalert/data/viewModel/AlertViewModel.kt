package com.example.comalert.data.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.comalert.data.local.Alert
import com.example.comalert.data.local.AlertDao
import com.example.comalert.data.repository.AlertRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AlertViewModel @Inject constructor(
    private val repository: AlertRepository,
    private val alertDao: AlertDao
) : ViewModel() {
    val recentAlerts: LiveData<List<Alert>> = repository.recentAlerts

    fun getAlertById(id: Int): LiveData<Alert> {
        return repository.getAlertById(id)
    }
    fun insertAlert(alert: Alert) {
        viewModelScope.launch {
            alertDao.insertAlert(alert)
        }
    }
}


