package com.example.comalert.presentation

import android.text.format.DateUtils
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import coil.compose.rememberAsyncImagePainter
import com.example.comalert.data.viewModel.AlertViewModel

@Composable
fun AlertScreen(navController: NavController, alertViewModel: AlertViewModel) {
    val alerts by alertViewModel.recentAlerts.observeAsState(emptyList())
    val scrollState = rememberScrollState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFF2E1C1A))
            .padding(4.dp)
            .statusBarsPadding()
            .navigationBarsPadding() // Adjust padding for bottom navigation bar
            .verticalScroll(scrollState)
            .padding(start = 8.dp, top = 8.dp, end = 8.dp, bottom = 58.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            "Alerts",
            fontSize = 24.sp,
            color = Color.White,
            fontWeight = FontWeight.SemiBold,
            modifier = Modifier.padding(top = 8.dp) // Add top padding to separate from status bar
        )
        Spacer(modifier = Modifier.height(14.dp))
        alerts.forEach { alert ->
            AlertItem(
                title = alert.title,
                description = alert.description.take(100) + "...", // Show only the first 100 characters
                time = DateUtils.getRelativeTimeSpanString(alert.timestamp).toString(),
                onClick = {
                    navController.navigate("alertDetails/${alert.id}") // Navigate to detail screen
                }
            )
        }
    }
}



@Composable
fun AlertItem(
    title: String,
    description: String,
    time: String,
    mediaUri: String? = null,
    onClick: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(onClick = onClick)
            .padding(vertical = 4.dp)
    ) {
        Text(
            text = title,
            color = Color.White,
            fontSize = 18.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(bottom = 3.dp)
        )
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Box(
                modifier = Modifier.weight(0.8f)
            ) {
                Text(
                    text = description.take(100) + if (description.length > 100) "..." else "",
                    color = Color.White,
                    fontSize = 14.sp,
                    modifier = Modifier.fillMaxWidth()
                )
            }
            Text(
                text = time,
                color = Color.Gray,
                fontSize = 12.sp,
                modifier = Modifier.padding(start = 8.dp)
            )
        }
        mediaUri?.let {
            Spacer(modifier = Modifier.height(8.dp))
            Image(
                painter = rememberAsyncImagePainter(it),
                contentDescription = null,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(200.dp)
                    .clip(RoundedCornerShape(8.dp))
                    .border(BorderStroke(2.dp, Color.White), RoundedCornerShape(8.dp))
            )
        }
        Spacer(modifier = Modifier.height(8.dp))
    }
}



@Preview(showBackground = true)
@Composable
private fun AlertScreenPreview(){

}
