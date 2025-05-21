package com.example.done_ishapp

import android.os.Bundle
import android.os.Parcel
import android.os.Parcelable
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.done_ishapp.ui.theme.DoneishAppTheme
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.sp

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            DoneishAppTheme {
                val navController = rememberNavController()
                NavHost(navController = navController, startDestination = "welcome") {

                    composable("welcome") {
                        DoneishWelcomeScreen(navController)
                    }

                    composable("login") {
                        DoneishLoginScreen(navController)
                    }

                    composable("dashboard") {
                        DashboardScreen(navController)
                    }

                    composable("decision") {
                        DecisionBreakerScreen(navController)
                    }

                    composable("momentum") {
                        MomentumTrackerScreen(navController)
                    }

                    composable("interrupts") {
                        SmartInterruptsScreen(navController)
                    }

                    composable("stubborn") {
                        StubbornModeScreen(navController)
                    }

                    composable("microcommit") {
                        MicroCommitTimerScreen(navController)
                    }
                }
            }
        }
    }
}
