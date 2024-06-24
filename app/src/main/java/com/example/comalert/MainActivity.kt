package com.example.comalert

import android.app.Application
import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.annotation.RequiresApi
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AddCircle
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.outlined.AddCircle
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material.icons.outlined.LocationOn
import androidx.compose.material.icons.outlined.Notifications
import androidx.compose.material.icons.outlined.Person
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navigation
import com.example.comalert.data.viewModel.AlertViewModel
import com.example.comalert.data.viewModel.AuthViewModel
import com.example.comalert.presentation.AlertDetailsScreen
import com.example.comalert.presentation.AlertScreen
import com.example.comalert.presentation.Auth.LogInScreen
import com.example.comalert.presentation.Auth.SignUpScreen
import com.example.comalert.presentation.HomeScreen
import com.example.comalert.presentation.MapScreen
import com.example.comalert.presentation.PostAlertScreen
import com.example.comalert.presentation.ProfileScreen
import com.example.comalert.ui.theme.ComAlertTheme
import com.example.comalert.data.viewModel.AuthState
import dagger.hilt.android.AndroidEntryPoint
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class MyApp : Application()

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    @RequiresApi(Build.VERSION_CODES.S)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            ComAlertTheme {
                NextDoorApp()
            }
        }
    }
}

@RequiresApi(Build.VERSION_CODES.S)
@Composable
fun NextDoorApp() {
    val navController = rememberNavController()
    val alertViewModel: AlertViewModel = hiltViewModel()
    val authViewModel: AuthViewModel = hiltViewModel()
    val authState by authViewModel.authState.observeAsState()

    Scaffold(
        bottomBar = {
            if (authState is AuthState.Authenticated) {
                NavigationBar(containerColor = Color(0xFF4E342E)) {
                    bottomNavItems.forEachIndexed { index, bottomNavItem ->
                        NavigationBarItem(
                            colors = NavigationBarItemDefaults.colors(Color.Black),
                            selected = navController.currentDestination?.route == bottomNavItem.route,
                            onClick = {
                                navController.navigate(bottomNavItem.route) {
                                    popUpTo(navController.graph.startDestinationId) {
                                        saveState = true
                                    }
                                    launchSingleTop = true
                                    restoreState = true
                                }
                            },
                            icon = {
                                Icon(
                                    imageVector = if (navController.currentDestination?.route == bottomNavItem.route) bottomNavItem.selectedIcon else bottomNavItem.unselectedIcon,
                                    contentDescription = bottomNavItem.title,
                                    tint = if (navController.currentDestination?.route == bottomNavItem.route) Color.Black else Color.White
                                )
                            },
                            label = { Text(text = bottomNavItem.title, color = Color.White) },
                            alwaysShowLabel = true,
                            interactionSource = MutableInteractionSource()
                        )
                    }
                }
            }
        }
    ) { innerPadding ->
        val padding = innerPadding
        CentralNavHost(
            navController = navController,
            authState = authState,
            alertViewModel = alertViewModel,
            authViewModel = authViewModel
        )

    }
}


@RequiresApi(Build.VERSION_CODES.S)
@Composable
fun CentralNavHost(
    navController: NavHostController = rememberNavController(),
    authState: AuthState?,
    alertViewModel: AlertViewModel = hiltViewModel(),
    authViewModel: AuthViewModel = hiltViewModel()
) {
    NavHost(
        navController = navController,
        startDestination = if (authState is AuthState.Authenticated) "main" else "auth"
    ) {
        navigation(startDestination = "login", route = "auth") {
            composable("login") { LogInScreen(navController, authViewModel) }
            composable("signup") { SignUpScreen(navController, authViewModel) }
        }
        navigation(startDestination = "home", route = "main") {
            composable("home") { HomeScreen(navController, alertViewModel) }
            composable("alerts") { AlertScreen(navController, alertViewModel) }
            composable("post") { PostAlertScreen(navController, alertViewModel) }
            composable("map") { MapScreen() }
            composable("profile") { ProfileScreen(navController,authViewModel) }
            composable("alertDetails/{alertId}") { backStackEntry ->
                val alertId = backStackEntry.arguments?.getString("alertId")?.toInt() ?: 0
                AlertDetailsScreen(alertId, navController, alertViewModel)
            }
        }
    }
}


val bottomNavItems = listOf(
    BottomNavItem(
        title = "Home",
        route = "Home",
        selectedIcon = Icons.Filled.Home,
        unselectedIcon = Icons.Outlined.Home
    ),
    BottomNavItem(
        title = "Alerts",
        route = "Alerts",
        selectedIcon = Icons.Filled.Notifications,
        unselectedIcon = Icons.Outlined.Notifications
    ),
    BottomNavItem(
        title = "Post",
        route = "Post",
        selectedIcon = Icons.Filled.AddCircle,
        unselectedIcon = Icons.Outlined.AddCircle
    ),
    BottomNavItem(
        title = "Map",
        route = "Map",
        selectedIcon = Icons.Filled.LocationOn,
        unselectedIcon = Icons.Outlined.LocationOn
    ),
    BottomNavItem(
        title = "Profile",
        route = "Profile",
        selectedIcon = Icons.Filled.Person,
        unselectedIcon = Icons.Outlined.Person
    )
)

data class BottomNavItem(
    val title: String,
    val route: String,
    val selectedIcon: ImageVector,
    val unselectedIcon: ImageVector
)

