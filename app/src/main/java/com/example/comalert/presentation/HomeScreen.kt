package com.example.comalert.presentation

import android.Manifest
import android.text.format.DateUtils
import android.widget.Toast
import  androidx.compose.foundation.background
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
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AddCircle
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.comalert.data.viewModel.AlertViewModel
import com.example.comalert.data.viewModel.AuthState
import com.example.comalert.data.viewModel.AuthViewModel
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.rememberMultiplePermissionsState

@OptIn(ExperimentalPermissionsApi::class)
@Composable
fun HomeScreen(navController: NavController, alertViewModel: AlertViewModel = hiltViewModel(), ) {
    val locationPermissionState = rememberMultiplePermissionsState(
        permissions = listOf(
            Manifest.permission.ACCESS_FINE_LOCATION,
            Manifest.permission.ACCESS_COARSE_LOCATION
        )
    )

    val recentAlerts by alertViewModel.recentAlerts.observeAsState(emptyList())


    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFF2E1C1A))
            .statusBarsPadding() // Add padding for status bar
            .navigationBarsPadding() // Add padding for navigation bar
            .padding(8.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        when {
            locationPermissionState.allPermissionsGranted -> {
                // Your normal HomeScreen content
                Text(
                    text = "Community Safety",
                    color = Color.White,
                    fontSize = 26.sp,
                    fontWeight = FontWeight.Bold
                )
                Spacer(modifier = Modifier.height(14.dp))
                Text(
                    text = "Welcome to Nextdoor!",
                    color = Color.White,
                    fontSize = 22.sp,
                    fontWeight = FontWeight.Bold
                )
                Spacer(modifier = Modifier.height(12.dp))
                Text(
                    text = "The private social network for your neighborhood.",
                    color = Color.Gray,
                    fontSize = 18.sp,
                    textAlign = TextAlign.Center
                )
                Spacer(modifier = Modifier.height(24.dp))
                OptionItem(icon = Icons.Default.Notifications, title = "Alert Feed", description = "Be aware of local safety issues.")
                OptionItem(icon = Icons.Default.AddCircle, title = "Post an alert", description = "Share information about a crime or suspicious activity.")
                OptionItem(icon = Icons.Default.Person, title = "Profile", description = "Access your profile and settings.")
                OptionItem(icon = Icons.Default.LocationOn, title = "Map View", description = "View all the alerts on a map.")
                Spacer(modifier = Modifier.height(16.dp))
                Text(
                    text = "Most recent alerts",
                    color = Color.White,
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.padding(horizontal = 8.dp) // Add horizontal padding to separate from edges
                )
                Spacer(modifier = Modifier.height(8.dp))
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .verticalScroll(rememberScrollState())
                        .background(Color(0xFF2E1C1A))
                        .navigationBarsPadding()
                        .padding(start = 8.dp, top = 8.dp, end = 8.dp, bottom = 58.dp) // Adjust bottom padding as needed
                ) {
                    recentAlerts.take(5).forEach { alert ->
                        AlertItem(
                            title = alert.title,
                            description = alert.description,
                            time = DateUtils.getRelativeTimeSpanString(alert.timestamp).toString()
                        ) {
                            navController.navigate("alertDetails/${alert.id}")
                        }
                    }
                }
            }
            locationPermissionState.shouldShowRationale || !locationPermissionState.allPermissionsGranted -> {
                // Show rationale or request permission
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center,
                    modifier = Modifier.fillMaxSize()
                ) {
                    Text(
                        text = "Location permission is needed to show your current location.",
                        color = Color.White,
                        fontSize = 18.sp,
                        textAlign = TextAlign.Center
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    Button(
                        onClick = { locationPermissionState.launchMultiplePermissionRequest() },
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(48.dp),
                        shape = RoundedCornerShape(6.dp),
                        colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFD32F2F))
                    ) {
                        Text(text = "Grant Permission", color = Color.White, fontSize = 16.sp)
                    }
                }
            }
        }
    }
}




@Composable
fun OptionItem(icon: ImageVector, title: String, description: String) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 10.dp)
    ) {
        Box(
            modifier = Modifier
                .size(40.dp)
                .background(Color(0xFF4E342E), shape = RoundedCornerShape(4.dp))
                .padding(6.dp),
            contentAlignment = Alignment.Center
        ) {
            Icon(imageVector = icon, contentDescription = title, tint = Color.White)
        }
        Spacer(modifier = Modifier.width(14.dp))
        Column {
            Text(text = title, color = Color.White, fontSize = 16.sp, fontWeight = FontWeight.Bold)
            Text(text = description, color = Color.Gray, fontSize = 14.sp)
        }
    }
}
