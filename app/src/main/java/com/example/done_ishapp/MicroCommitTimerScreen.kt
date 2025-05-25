
package com.example.done_ishapp

import androidx.compose.animation.core.*
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
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
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.draw.alpha

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MicroCommitTimerScreen(navController: NavController) {
    var showCelebrate by remember { mutableStateOf(false) }

    // Pulse animation for the celebratory message
    val infiniteTransition = rememberInfiniteTransition()
    val pulseAlpha by infiniteTransition.animateFloat(
        initialValue = 0.3f,
        targetValue = 1f,
        animationSpec = infiniteRepeatable(
            animation = tween(durationMillis = 800, easing = FastOutSlowInEasing),
            repeatMode = RepeatMode.Reverse
        )
    )

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("The Minimum Effort Club", fontSize = 20.sp) },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(Icons.Filled.ArrowBack, contentDescription = "Back")
                    }
                }
            )
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .padding(innerPadding)
                .padding(24.dp)
                .fillMaxSize(),
            verticalArrangement = Arrangement.SpaceBetween,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Column {
                TaskButton("2 MIN", "Put Away Laundry")
                Spacer(modifier = Modifier.height(8.dp))
                TaskButton("5 MIN", "Clear Kitchen Counter")
                Spacer(modifier = Modifier.height(8.dp))
                TaskButton("15 MIN", "Organize One Drawer")

                Spacer(modifier = Modifier.height(24.dp))

                ChecklistItem("Fold 5 items", true)
                ChecklistItem("Put away 5 items", true)
                ChecklistItem("Prepare 1 coffee", true)
            }

            Row(horizontalArrangement = Arrangement.spacedBy(16.dp)) {
                Button(
                    onClick = { showCelebrate = true },
                    colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFFFB74D)),
                    modifier = Modifier.weight(1f)
                ) {
                    Text("Celebrate", color = Color.White)
                }

                Button(
                    onClick = { navController.popBackStack() },
                    colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFD84315)),
                    modifier = Modifier.weight(1f)
                ) {
                    Text("Continue", color = Color.White)
                }
            }

            if (showCelebrate) {
                Text(
                    text = " Yay! ",
                    fontSize = 32.sp,
                    color = Color.Magenta,
                    modifier = Modifier.alpha(pulseAlpha)
                )
            }
        }
    }
}

@Composable
fun TaskButton(time: String, task: String) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(Color(0xFF00695C), RoundedCornerShape(12.dp))
            .padding(16.dp)
    ) {
        Text(time, color = Color.White, fontSize = 16.sp)
        Spacer(modifier = Modifier.width(16.dp))
        Text(task, color = Color.White, fontSize = 16.sp)
    }
}

@Composable
fun ChecklistItem(text: String, checked: Boolean) {
    Row(verticalAlignment = Alignment.CenterVertically) {
        Checkbox(checked = checked, onCheckedChange = null)
        Spacer(modifier = Modifier.width(8.dp))
        Text(text, fontSize = 16.sp)
    }
}

@Preview(showBackground = true)
@Composable
fun MicroCommitTimerScreenPreview() {
    DoneishAppTheme {
        MicroCommitTimerScreen(navController = rememberNavController())
    }
}
