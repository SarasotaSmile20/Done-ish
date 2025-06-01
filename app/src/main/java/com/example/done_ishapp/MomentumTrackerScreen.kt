package com.example.done_ishapp

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.scaleIn
import androidx.compose.animation.scaleOut
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.compose.rememberNavController
import com.example.done_ishapp.ui.theme.*
import kotlinx.coroutines.delay

// Level data
data class Level(val name: String, val minPoints: Int)

val levels = listOf(
    Level("Rookie", 0),
    Level("Rising Star", 100),
    Level("Trailblazer", 300),
    Level("Champion", 600),
    Level("Legend", 1000)
)

@Composable
fun MomentumTrackerScreen(navController: NavController) {
    var coinCount by remember { mutableStateOf(250) }
    var showWinDialog by remember { mutableStateOf(false) }
    var showConfetti by remember { mutableStateOf(false) }
    var winText by remember { mutableStateOf(TextFieldValue("")) }
    var wins by remember { mutableStateOf(listOf<String>()) }
    var showWinsDialog by remember { mutableStateOf(false) }

    // LEVEL LOGIC
    val currentLevelIndex = levels.indexOfLast { coinCount >= it.minPoints }.coerceAtLeast(0)
    val currentLevel = levels[currentLevelIndex]
    val nextLevel = levels.getOrNull(currentLevelIndex + 1)
    val levelStart = currentLevel.minPoints
    val levelEnd = nextLevel?.minPoints ?: (coinCount + 100) // Extend if last level
    val progress = (coinCount - levelStart).toFloat() / (levelEnd - levelStart).toFloat()

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(SucculentGreen)
            .padding(24.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize(),
            verticalArrangement = Arrangement.SpaceBetween,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Spacer(modifier = Modifier.height(12.dp))

            Text(
                text = "Momentum Tracker",
                fontSize = 28.sp,
                color = SucculentBrown,
                fontWeight = FontWeight.Bold
            )

            Spacer(modifier = Modifier.height(6.dp))

            // Circular progress donut
            Box(contentAlignment = Alignment.Center, modifier = Modifier.size(175.dp)) {
                CircularProgressBar(
                    progress = progress.coerceIn(0f, 1f),
                    color = SucculentButton,
                    trackColor = SucculentSurface,
                    strokeWidth = 14f
                )
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Text(
                        text = "Level",
                        color = SucculentBrown,
                        fontSize = 16.sp
                    )
                    Text(
                        text = currentLevel.name,
                        color = SucculentBrown,
                        fontSize = 24.sp,
                        fontWeight = FontWeight.Bold
                    )
                    Text(
                        text = "${coinCount} pts",
                        color = SucculentOnBackground,
                        fontSize = 15.sp
                    )
                }
            }

            if (nextLevel != null) {
                Text(
                    text = "Next: ${nextLevel.name} (${levelEnd - coinCount} pts left)",
                    color = SucculentOnBackground,
                    fontSize = 14.sp
                )
            } else {
                Text(
                    text = "You’ve reached the top level!",
                    color = SucculentOnBackground,
                    fontSize = 14.sp
                )
            }

            Spacer(modifier = Modifier.height(10.dp))

            // Enter win
            OutlinedTextField(
                value = winText,
                onValueChange = { winText = it },
                label = { Text("What did you just win at?") },
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(16.dp),
                colors = OutlinedTextFieldDefaults.colors(
                    focusedBorderColor = SucculentBrown,
                    unfocusedBorderColor = SucculentBrown,
                    cursorColor = SucculentBrown,
                    focusedLabelColor = SucculentBrown,
                    unfocusedLabelColor = SucculentBrown,
                    focusedTextColor = SucculentBrown,
                    unfocusedTextColor = SucculentBrown
                )
            )

            Spacer(modifier = Modifier.height(12.dp))

            Button(
                onClick = {
                    if (winText.text.isNotBlank()) {
                        coinCount += 10
                        wins = wins + winText.text
                        winText = TextFieldValue("")
                        showWinDialog = true
                        showConfetti = true
                    }
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(50.dp),
                shape = RoundedCornerShape(16.dp),
                colors = ButtonDefaults.buttonColors(containerColor = SucculentButton)
            ) {
                Text("Log My Win", color = SucculentBrown, fontWeight = FontWeight.Bold)
            }

            Button(
                onClick = { showWinsDialog = true },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(46.dp)
                    .border(1.dp, SucculentBrown, shape = RoundedCornerShape(16.dp)),
                colors = ButtonDefaults.buttonColors(containerColor = Color.Transparent)
            ) {
                Text("View My Wins", color = SucculentBrown)
            }

            Spacer(modifier = Modifier.height(8.dp))

            Text(
                "Every little win counts. Future You is grateful!",
                color = SucculentBrown,
                fontSize = 15.sp
            )

            Spacer(modifier = Modifier.height(6.dp))
        }

        // Confetti/firework animation on coin gain
        AnimatedVisibility(
            visible = showConfetti,
            enter = fadeIn() + scaleIn(initialScale = 2f),
            exit = fadeOut() + scaleOut(targetScale = 0.1f),
            modifier = Modifier.align(Alignment.TopCenter)
        ) {
            FireworkConfetti()
        }

        // Win dialog
        if (showWinDialog) {
            AlertDialog(
                onDismissRequest = { showWinDialog = false; showConfetti = false },
                confirmButton = {
                    TextButton(onClick = { showWinDialog = false; showConfetti = false }) {
                        Text("Nice!", color = SucculentBrown)
                    }
                },
                title = { Text("🎉 +10 Coins!", color = SucculentBrown) },
                text = { Text("Win logged. Keep it up!", color = SucculentOnBackground) }
            )
        }

        // Wins dialog
        if (showWinsDialog) {
            AlertDialog(
                onDismissRequest = { showWinsDialog = false },
                confirmButton = {
                    TextButton(onClick = { showWinsDialog = false }) {
                        Text("Close", color = SucculentBrown)
                    }
                },
                title = { Text("My Wins", color = SucculentBrown) },
                text = {
                    if (wins.isEmpty()) Text("No wins logged yet.", color = SucculentOnBackground)
                    else Column {
                        wins.forEach { Text("• $it", color = SucculentOnBackground, fontSize = 15.sp) }
                    }
                }
            )
        }
    }
}

@Composable
fun CircularProgressBar(
    progress: Float,
    color: Color,
    trackColor: Color,
    strokeWidth: Float
) {
    Canvas(modifier = Modifier.size(170.dp)) {
        drawArc(
            color = trackColor,
            startAngle = 0f,
            sweepAngle = 360f,
            useCenter = false,
            style = Stroke(width = strokeWidth)
        )
        drawArc(
            color = color,
            startAngle = -90f,
            sweepAngle = 360 * progress,
            useCenter = false,
            style = Stroke(width = strokeWidth, cap = StrokeCap.Round)
        )
    }
}

@Composable
fun FireworkConfetti() {
    // Simple "burst" circles as placeholder for firework/confetti effect
    val colors = listOf(
        Color(0xFFFFA726), Color(0xFF66BB6A), Color(0xFF26A69A),
        Color(0xFFFFD54F), Color(0xFFEF5350)
    )
    val anim = rememberInfiniteTransition(label = "firework")
    val scale by anim.animateFloat(
        initialValue = 1f,
        targetValue = 1.7f,
        animationSpec = infiniteRepeatable(
            animation = tween(600, easing = FastOutSlowInEasing),
            repeatMode = androidx.compose.animation.core.RepeatMode.Reverse
        ), label = "scale"
    )
    Row(Modifier.padding(top = 40.dp), horizontalArrangement = Arrangement.Center) {
        colors.forEach { c ->
            Box(
                Modifier
                    .size((22 * scale).dp)
                    .padding(horizontal = 3.dp)
                    .clip(CircleShape)
                    .background(c.copy(alpha = 0.5f))
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun MomentumTrackerPreview() {
    DoneishAppTheme {
        MomentumTrackerScreen(navController = rememberNavController())
    }
}
