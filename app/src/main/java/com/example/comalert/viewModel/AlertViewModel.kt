package com.example.comalert.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.comalert.data.remote.Alert
import com.example.comalert.data.remote.AlertRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AlertViewModel @Inject constructor(
    private val repository: AlertRepository
) : ViewModel() {
    val recentAlerts: LiveData<List<Alert>> = repository.recentAlerts

    fun getAlertById(id: String): LiveData<Alert?> {
        val alertLiveData = MutableLiveData<Alert?>()
        viewModelScope.launch {
            val alert = repository.getAlertById(id)
            alertLiveData.postValue(alert)
        }
        return alertLiveData
    }

    fun insertAlert(alert: Alert) {
        viewModelScope.launch {
            repository.insertAlert(alert)
        }
    }
}
