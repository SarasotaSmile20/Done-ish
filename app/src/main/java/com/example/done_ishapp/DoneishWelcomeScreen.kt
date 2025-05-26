package com.example.done_ishapp

import androidx.compose.animation.core.*
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.zIndex
import com.example.done_ishapp.ui.theme.DoneishAppTheme
import com.example.done_ishapp.R

@Composable
fun DoneishWelcomeScreen(navController: NavController) {
    val moods = listOf("Foggy Brain", "Overwhelmed", "Just Need a Nudge", "Sorta Ready")
    val moodMessages = mapOf(
        "Foggy Brain" to listOf(
            "Let’s try just 2 minutes. That’s enough.",
            "You don’t need to think clearly to start. Just start.",
            "One foggy step still moves you forward."
        ),
        "Overwhelmed" to listOf(
            "Let’s pick *one* tiny step. You’re not alone.",
            "Everything doesn’t need to be done today.",
            "One brick at a time still builds a house."
        ),
        "Just Need a Nudge" to listOf(
            "I got you. Let’s break the inertia together.",
            "Small spark. Big shift.",
            "One action is all it takes to get rolling."
        ),
        "Sorta Ready" to listOf(
            "Let’s build on that spark. Want to start something?",
            "Ride the wave—you’re almost already in motion.",
            "Sorta ready is ready enough!"
        )
    )

    var selectedMood by remember { mutableStateOf<String?>(null) }
    var message by remember { mutableStateOf("") }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFF87CEEB)) // Sky blue
    ) {
        // Cloud Animation
        BoxWithConstraints(modifier = Modifier.fillMaxSize().zIndex(0f)) {
            val screenWidth = constraints.maxWidth
            val screenWidthDp = with(LocalDensity.current) { screenWidth.toDp() }

            val infiniteTransition = rememberInfiniteTransition()
            val offsetX by infiniteTransition.animateFloat(
                initialValue = 0f,
                targetValue = screenWidthDp.value,
                animationSpec = infiniteRepeatable(
                    animation = tween(durationMillis = 20000, easing = LinearEasing),
                    repeatMode = RepeatMode.Restart
                )
            )

            val offsetDp = offsetX.dp

            Image(
                painter = painterResource(R.drawable.clouds),
                contentDescription = null,
                contentScale = ContentScale.FillBounds,
                modifier = Modifier
                    .fillMaxSize()
                    .offset(x = -offsetDp)
            )

            Image(
                painter = painterResource(R.drawable.clouds),
                contentDescription = null,
                contentScale = ContentScale.FillBounds,
                modifier = Modifier
                    .fillMaxSize()
                    .offset(x = screenWidthDp - offsetDp)
            )
        }

        // Foreground UI
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(24.dp)
                .verticalScroll(rememberScrollState())
                .zIndex(1f),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Text(
                text = "Hey, you're here. That’s already a win.",
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(bottom = 16.dp),
                color = Color.Black
            )

            Text(text = "How are you showing up today?", fontSize = 18.sp, color = Color.Black)

            Spacer(modifier = Modifier.height(16.dp))

            moods.forEach { mood ->
                val isSelected = selectedMood == mood
                Button(
                    onClick = {
                        selectedMood = mood
                        message = ""
                    },
                    colors = ButtonDefaults.buttonColors(
                        containerColor = if (isSelected) Color(0xFFB3E5FC) else Color(0xFFE0F7FA),
                        contentColor = Color.Black
                    ),
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 4.dp)
                        .border(1.dp, Color.Black, shape = MaterialTheme.shapes.small)
                ) {
                    Text(text = mood)
                }
            }

            Spacer(modifier = Modifier.height(24.dp))

            Button(
                onClick = {
                    message = selectedMood?.let { mood ->
                        moodMessages[mood]?.random() ?: ""
                    } ?: ""
                },
                enabled = selectedMood != null,
                modifier = Modifier.fillMaxWidth(),
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFFFA726))
            ) {
                Text("Give Me Something Gentle", color = Color.White)
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
                    modifier = Modifier.fillMaxWidth(),
                    colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF00796B))
                ) {
                    Text("Continue to Login", color = Color.White)
                }
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
