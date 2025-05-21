package com.example.done_ishapp

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import androidx.compose.ui.tooling.preview.Preview
import com.example.done_ishapp.ui.theme.DoneishAppTheme

@Composable
fun DoneishWelcomeScreen(navController: NavController) {
    val moods = listOf("Foggy Brain", "Overwhelmed", "Just Need a Nudge", "Sorta Ready")
    var selectedMood by remember { mutableStateOf<String?>(null) }
    var message by remember { mutableStateOf("") }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp)
            .verticalScroll(rememberScrollState()),
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            text = "Hey, you're here. That’s already a win.",
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(bottom = 16.dp)
        )

        Text(text = "How are you showing up today?", fontSize = 18.sp)

        Spacer(modifier = Modifier.height(16.dp))

        moods.forEach { mood ->
            val isSelected = selectedMood == mood
            Button(
                onClick = { selectedMood = mood },
                colors = ButtonDefaults.buttonColors(
                    containerColor = if (isSelected) Color(0xFFB3E5FC) else Color(0xFFE0F7FA),
                    contentColor = Color.Black
                ),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 4.dp)
            ) {
                Text(text = mood)
            }
        }

        Spacer(modifier = Modifier.height(24.dp))

        Button(
            onClick = {
                message = when (selectedMood) {
                    "Foggy Brain" -> "Let’s try just 2 minutes. That’s enough."
                    "Overwhelmed" -> "Let’s pick *one* tiny step. You’re not alone."
                    "Just Need a Nudge" -> "I got you. Let’s break the inertia together."
                    "Sorta Ready" -> "Let’s build on that spark. Want to start something?"
                    else -> "Pick a mood above so I can meet you there 💛"
                }
            },
            enabled = selectedMood != null,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Give Me Something Gentle")
        }

        if (message.isNotEmpty()) {
            Spacer(modifier = Modifier.height(24.dp))
            Text(
                text = message,
                fontSize = 18.sp,
                fontStyle = FontStyle.Italic,
                color = Color.DarkGray
            )
        }

        if (selectedMood != null) {
            Spacer(modifier = Modifier.height(32.dp))
            Button(
                onClick = { navController.navigate("login") },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Continue to Login")
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun DoneishWelcomeScreenPreview() {
    DoneishAppTheme {
        DoneishWelcomeScreen(navController = rememberNavController())
    }
}
