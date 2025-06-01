package com.example.done_ishapp

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.compose.ui.tooling.preview.Preview
import com.example.done_ishapp.ui.theme.DoneishAppTheme
import androidx.navigation.compose.rememberNavController

// Import succulent theme colors from Color.kt (do NOT declare them here)
import com.example.done_ishapp.ui.theme.SucculentGreen
import com.example.done_ishapp.ui.theme.SucculentBrown
import com.example.done_ishapp.ui.theme.SucculentButton

@Composable
fun DashboardScreen(navController: NavController) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(SucculentGreen)
            .padding(horizontal = 24.dp, vertical = 28.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .align(Alignment.TopCenter),
            verticalArrangement = Arrangement.spacedBy(18.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = "Main Dashboard",
                fontSize = 28.sp,
                fontWeight = FontWeight.Bold,
                color = SucculentBrown
            )

            Text(
                text = "Choose what you want to work on today.",
                fontSize = 16.sp,
                color = SucculentBrown.copy(alpha = 0.7f),
                fontWeight = FontWeight.Medium,
                modifier = Modifier.padding(bottom = 10.dp)
            )

            DashboardButton("Decision Breaker") {
                navController.navigate("decision")
            }
            DashboardButton("Momentum Tracker") {
                navController.navigate("momentum")
            }
            DashboardButton("Smart Interrupt Notifications") {
                navController.navigate("interrupts")
            }
            DashboardButton("Stubborn Mode") {
                navController.navigate("stubborn")
            }
            DashboardButton("Micro-Commit Timer") {
                navController.navigate("microcommit")
            }
        }

        // Log Out button at the bottom
        Button(
            onClick = {
                navController.navigate("welcome") {
                    // Clear back stack to prevent return to dashboard on back press
                    popUpTo("dashboard") { inclusive = true }
                }
            },
            modifier = Modifier
                .fillMaxWidth()
                .height(56.dp)
                .align(Alignment.BottomCenter)
                .padding(bottom = 10.dp),
            shape = RoundedCornerShape(18.dp),
            colors = ButtonDefaults.buttonColors(containerColor = SucculentBrown)
        ) {
            Text(
                text = "Log Out",
                fontWeight = FontWeight.Bold,
                color = Color.White,
                fontSize = 18.sp
            )
        }
    }
}

@Composable
fun DashboardButton(label: String, onClick: () -> Unit) {
    Button(
        onClick = onClick,
        modifier = Modifier
            .fillMaxWidth()
            .height(60.dp),
        shape = RoundedCornerShape(16.dp),
        colors = ButtonDefaults.buttonColors(containerColor = SucculentButton)
    ) {
        Text(
            text = label,
            color = SucculentBrown,
            fontSize = 17.sp,
            fontWeight = FontWeight.SemiBold
        )
    }
}

@Preview(showBackground = true)
@Composable
fun DashboardScreenPreview() {
    DoneishAppTheme {
        DashboardScreen(navController = rememberNavController())
    }
}
