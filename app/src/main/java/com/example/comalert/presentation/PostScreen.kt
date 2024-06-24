package com.example.comalert.presentation

import android.net.Uri
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.scrollable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Image
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import coil.compose.rememberAsyncImagePainter
import com.example.comalert.data.local.Alert
import com.example.comalert.data.viewModel.AlertViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PostAlertScreen(navController: NavController, alertViewModel: AlertViewModel) {
    var title by remember { mutableStateOf("") }
    var description by remember { mutableStateOf("") }
    var urgencyLevel by remember { mutableStateOf("Low") }
    var mediaUri by remember { mutableStateOf<Uri?>(null) }

    val context = LocalContext.current
    val launcher = rememberLauncherForActivityResult(ActivityResultContracts.GetContent()) { uri: Uri? ->
        mediaUri = uri
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        "Create an alert",
                        color = Color.White,
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Bold
                    )
                },
                navigationIcon = {
                    Icon(
                        imageVector = Icons.Default.Close,
                        contentDescription = "Close",
                        tint = Color.White,
                        modifier = Modifier
                            .clickable { navController.navigateUp() }
                            .padding(16.dp)
                    )
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = Color(0xFF2E1C1A)
                )
            )
        },
        containerColor = Color(0xFF2E1C1A)
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(16.dp)
                .background(Color(0xFF2E1C1A))
                .navigationBarsPadding()
                .verticalScroll(rememberScrollState())
                .padding(start = 8.dp, top = 8.dp, end = 8.dp, bottom = 58.dp)
        ) {
            OutlinedTextField(
                value = title,
                onValueChange = { title = it },
                label = { Text("Title", color = Color.White) },
                placeholder = { Text("Add a title", color = Color(0xFF5B3A39)) },
                modifier = Modifier
                    .fillMaxWidth(),
                colors = TextFieldDefaults.colors(
                    focusedTextColor = Color.White,
                    unfocusedTextColor = Color.White,
                    focusedContainerColor = Color(0xFF422C2B),
                    unfocusedContainerColor = Color(0xFF422C2B),
                    cursorColor = Color.White,
                    focusedIndicatorColor = Color.Transparent,
                    unfocusedIndicatorColor = Color.Transparent,
                    focusedLabelColor = Color.White,
                    unfocusedLabelColor = Color.White,
                    focusedPlaceholderColor = Color(0xFF5B3A39),
                    unfocusedPlaceholderColor = Color(0xFF5B3A39)
                )
            )
            Spacer(modifier = Modifier.height(16.dp))
            OutlinedTextField(
                value = description,
                onValueChange = { description = it },
                label = { Text("Description", color = Color.White) },
                placeholder = { Text("Add a description", color = Color(0xFF5B3A39)) },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(180.dp),
                colors = TextFieldDefaults.colors(
                    focusedTextColor = Color.White,
                    unfocusedTextColor = Color.White,
                    focusedContainerColor = Color(0xFF422C2B),
                    unfocusedContainerColor = Color(0xFF422C2B),
                    cursorColor = Color.White,
                    focusedIndicatorColor = Color.Transparent,
                    unfocusedIndicatorColor = Color.Transparent,
                    focusedLabelColor = Color.White,
                    unfocusedLabelColor = Color.White,
                    focusedPlaceholderColor = Color(0xFF5B3A39),
                    unfocusedPlaceholderColor = Color(0xFF5B3A39)
                )
            )
            Spacer(modifier = Modifier.height(26.dp))
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                    .fillMaxWidth()
            ) {
                Text("Add media", color = Color.White, fontWeight = FontWeight.Bold)
                Spacer(modifier = Modifier.weight(1f))
                Icon(
                    imageVector = Icons.Default.Image,
                    contentDescription = "Add media",
                    tint = Color.White,
                    modifier = Modifier.clickable {
                        launcher.launch("image/*")
                    }
                )
            }
            mediaUri?.let {
                Spacer(modifier = Modifier.height(16.dp))
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
            Spacer(modifier = Modifier.height(16.dp))
            Text("Urgency level", color = Color.White, fontWeight = FontWeight.Bold)
            Spacer(modifier = Modifier.height(8.dp))
            UrgencyLevelOption("Low", urgencyLevel) { urgencyLevel = it }
            UrgencyLevelOption("Medium", urgencyLevel) { urgencyLevel = it }
            UrgencyLevelOption("High", urgencyLevel) { urgencyLevel = it }
            Spacer(modifier = Modifier.height(24.dp))
            Button(
                onClick = {
                    if (title.isNotEmpty() && description.isNotEmpty()) {
                        alertViewModel.insertAlert(
                            Alert(
                                title = title,
                                description = description,
                                urgencyLevel = urgencyLevel,
                                timestamp = System.currentTimeMillis(),
                                mediaUri = mediaUri?.toString()
                            )
                        )
                        navController.navigateUp()
                    } else {
                        Toast.makeText(context, "Title and description cannot be empty", Toast.LENGTH_SHORT).show()
                    }
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(48.dp),
                shape = RoundedCornerShape(8.dp),
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFD32F2F))
            ) {
                Text("Post", color = Color.White, fontSize = 16.sp)
            }
        }
    }
}


@Composable
fun UrgencyLevelOption(level: String, selectedLevel: String, onSelected: (String) -> Unit) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onSelected(level) }
            .padding(vertical = 8.dp)
            .border(BorderStroke(1.dp, Color.Gray), RoundedCornerShape(8.dp))
            .padding(horizontal = 16.dp) // Inner padding
    ) {
        Text(level, color = Color.White)
        Spacer(modifier = Modifier.weight(1f))
        RadioButton(
            selected = level == selectedLevel,
            onClick = { onSelected(level) },
            colors = RadioButtonDefaults.colors(selectedColor = Color(0xFFD32F2F), unselectedColor = Color.Gray)
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun PostAlertScreenPreview() {

}
