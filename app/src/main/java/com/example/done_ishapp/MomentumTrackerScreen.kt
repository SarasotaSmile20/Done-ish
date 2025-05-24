package com.example.done_ishapp

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.compose.rememberNavController
import com.example.done_ishapp.ui.theme.DoneishAppTheme

@Composable
fun MomentumTrackerScreen(navController: NavController) {
    var coinCount by remember { mutableStateOf(250) }
    var showWin by remember { mutableStateOf(false) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),
        verticalArrangement = Arrangement.SpaceBetween,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        if (showWin) {
            Text(
                text = "You Did\nSomething!",
                fontSize = 32.sp,
                color = Color(0xFFB83B1D),
                lineHeight = 36.sp
            )

            // Placeholder for confetti or donut chart animation
            Text("+10 Coins Earned!", fontSize = 22.sp, color = Color(0xFFB83B1D))

            Spacer(modifier = Modifier.height(16.dp))

            Text("Another win for today", fontSize = 16.sp)
        }

        Spacer(modifier = Modifier.height(24.dp))

        Divider(thickness = 1.dp, color = Color.LightGray)

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text("Your Coins:\n${coinCount}", fontSize = 20.sp, color = Color(0xFFB83B1D))
            Column(horizontalAlignment = Alignment.End) {
                Text("Level: Momentum", fontSize = 14.sp, color = Color(0xFFB83B1D))
                Text("Rookie", fontSize = 18.sp, fontWeight = MaterialTheme.typography.titleMedium.fontWeight)
                LinearProgressIndicator(
                    progress = 0.3f,
                    modifier = Modifier
                        .width(140.dp)
                        .height(8.dp),
                    color = Color(0xFF00796B)
                )
            }
        }

        Spacer(modifier = Modifier.height(24.dp))

        Button(
            onClick = {
                coinCount += 10
                showWin = true
                // Optional: Trigger animation
            },
            modifier = Modifier
                .fillMaxWidth()
                .height(50.dp),
            shape = RoundedCornerShape(16.dp),
            colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFEF6C00))
        ) {
            Text("Log Another Win", color = Color.White)
        }

        Button(
            onClick = { /* Navigate to Win History */ },
            modifier = Modifier
                .fillMaxWidth()
                .height(50.dp)
                .border(1.dp, Color(0xFF00796B), shape = RoundedCornerShape(16.dp)),
            colors = ButtonDefaults.buttonColors(containerColor = Color.Transparent)
        ) {
            Text("View My Wins", color = Color(0xFF00796B))
        }

        Button(
            onClick = { /* Navigate to Spend Coins page */ },
            modifier = Modifier
                .fillMaxWidth()
                .height(50.dp)
                .border(1.dp, Color(0xFF00796B), shape = RoundedCornerShape(16.dp)),
            colors = ButtonDefaults.buttonColors(containerColor = Color.Transparent)
        ) {
            Text("Spend Coins", color = Color(0xFF00796B))
        }

        Spacer(modifier = Modifier.height(16.dp))
        Text("Future You is ever so grate ful!", fontSize = 14.sp, color = Color(0xFFB83B1D))
    }
}

@Preview(showBackground = true)
@Composable
fun MomentumTrackerPreview() {
    DoneishAppTheme {
        MomentumTrackerScreen(navController = rememberNavController())
    }
}
