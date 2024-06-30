package com.example.comalert.presentation

import android.annotation.SuppressLint
import android.location.Location
import android.location.LocationRequest
import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import com.example.comalert.viewModel.AlertViewModel
import com.google.android.gms.location.LocationServices
import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.compose.GoogleMap
import com.google.maps.android.compose.MapUiSettings
import com.google.maps.android.compose.Marker

@RequiresApi(Build.VERSION_CODES.S)
@Composable
@SuppressLint("MissingPermission")
fun MapScreen(alertViewModel: AlertViewModel) {
    var searchQuery by remember { mutableStateOf("") }
    var selectedUrgency by remember { mutableStateOf("All") }

    val uiSettings by remember {
        mutableStateOf(MapUiSettings(zoomControlsEnabled = true))
    }

    val alerts by alertViewModel.recentAlerts.observeAsState(emptyList())

    Box(
        modifier = Modifier
            .fillMaxSize()
    ) {
        // Map
        GoogleMap(
            modifier = Modifier.fillMaxSize(),
            uiSettings = uiSettings,

            ) {
            // Markers for Alerts
            alerts.forEach { alert ->
                Marker(
                    position = LatLng(alert.latitude, alert.longitude),
                    title = alert.title,
                    snippet = alert.description
                )
            }
        }

        // Search Bar and Filters
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
                .statusBarsPadding()
                .navigationBarsPadding(),
            verticalArrangement = Arrangement.SpaceBetween,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            // Search Bar
            BasicTextField(
                value = searchQuery,
                onValueChange = { searchQuery = it },
                modifier = Modifier
                    .fillMaxWidth()
                    .background(Color(0xFF4E342E), shape = RoundedCornerShape(8.dp))
                    .padding(8.dp)
            ) {
                Row(modifier = Modifier.fillMaxWidth()) {
                    Icon(
                        Icons.Default.Search,
                        contentDescription = "search Icon",
                        tint = Color.LightGray
                    )
                    Spacer(modifier = Modifier.padding(start = 8.dp))
                    if (searchQuery.isEmpty()) {
                        Text(text = "Search map", color = Color.LightGray)
                    } else {
                        Text(text = searchQuery, color = Color.White)
                    }
                }
            }
            Spacer(modifier = Modifier.height(8.dp))
            // Filters
            LazyRow(
                modifier = Modifier
                    .fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                item {
                    FilterButton(text = "All", isSelected = selectedUrgency == "All") {
                        selectedUrgency = "All"
                    }
                    FilterButton(text = "High", isSelected = selectedUrgency == "High") {
                        selectedUrgency = "High"
                    }
                    FilterButton(text = "Medium", isSelected = selectedUrgency == "Medium") {
                        selectedUrgency = "Medium"
                    }
                    FilterButton(text = "Low", isSelected = selectedUrgency == "Low") {
                        selectedUrgency = "Low"
                    }
                }
            }
        }
    }
}


@Composable
fun FilterButton(text: String, isSelected: Boolean, onClick: () -> Unit) {
    Button(
        onClick = onClick,
        modifier = Modifier
            .padding(horizontal = 3.dp),
        shape = RoundedCornerShape(8.dp),
        colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF4E342E))

    ) {
        Text(text = text, color = Color.White)
    }
}
