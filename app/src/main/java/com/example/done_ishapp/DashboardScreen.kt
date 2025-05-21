package com.example.done_ishapp

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

@Composable
fun DashboardScreen(navController: NavController) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),
        verticalArrangement = Arrangement.spacedBy(20.dp, Alignment.CenterVertically),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "Main Dashboard",
            fontSize = 28.sp,
            fontWeight = FontWeight.Bold,
            color = Color(0xFFB83B1D)
        )

        DashboardButton("Decision Breaker") {
            // navController.navigate("decision")
        }

        DashboardButton("Momentum Tracker") {
            // navController.navigate("momentum")
        }

        DashboardButton("Smart Interrupt Notifications") {
            // navController.navigate("interrupts")
        }

        DashboardButton("Stubborn Mode") {
            // navController.navigate("stubborn")
        }

        DashboardButton("Micro-Commit Timer") {
            // navController.navigate("micro-commitment")
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
        colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFFFCC80))
    ) {
        Text(text = label, color = Color.DarkGray, fontSize = 16.sp)
    }
}

@Preview(showBackground = true)
@Composable
fun DashboardScreenPreview() {
    DoneishAppTheme {
        DashboardScreen(navController = rememberNavController())
    }
}

