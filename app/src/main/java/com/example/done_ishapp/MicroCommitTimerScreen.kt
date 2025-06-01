package com.example.done_ishapp

import androidx.compose.animation.core.*
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.compose.rememberNavController
import com.example.done_ishapp.ui.theme.*

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MicroCommitTimerScreen(navController: NavController) {
    val microCommitOptions = listOf(
        MicroCommitRow(
            minutes = 2,
            label = "2 MIN",
            tasks = listOf(
                "Put Away Laundry",
                "Water One Plant",
                "Send One Email",
                "Wipe Down Counter",
                "Write a Text Reply"
            )
        ),
        MicroCommitRow(
            minutes = 5,
            label = "5 MIN",
            tasks = listOf(
                "Clear Kitchen Counter",
                "Sort Mail",
                "Walk Around Block",
                "Empty Trash",
                "Prep Snack"
            )
        ),
        MicroCommitRow(
            minutes = 15,
            label = "15 MIN",
            tasks = listOf(
                "Organize One Drawer",
                "Read 5 Pages",
                "Meditate",
                "Vacuum Room",
                "Declutter Desk"
            )
        )
    )

    var selected by remember { mutableStateOf<SelectedTask?>(null) }
    var timeLeft by remember { mutableStateOf(0) }
    var timerRunning by remember { mutableStateOf(false) }
    var showCelebrate by remember { mutableStateOf(false) }

    LaunchedEffect(timerRunning, timeLeft) {
        if (timerRunning && timeLeft > 0) {
            kotlinx.coroutines.delay(1000)
            timeLeft -= 1
        } else if (timerRunning && timeLeft == 0 && selected != null) {
            timerRunning = false
            showCelebrate = true
        }
    }

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
        containerColor = SucculentGreen,
        topBar = {
            TopAppBar(
                title = {
                    Text("The Minimum Effort Club", fontSize = 20.sp, color = SucculentBrown)
                },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Back", tint = SucculentBrown)
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(containerColor = SucculentSurface)
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
            Column(
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                microCommitOptions.forEach { row ->
                    MicroCommitRowSelector(
                        row = row,
                        selectedTask = selected,
                        onSelect = { taskLabel ->
                            val isSame = selected?.rowLabel == row.label && selected?.taskLabel == taskLabel
                            if (isSame) {
                                selected = null
                                timerRunning = false
                                showCelebrate = false
                            } else {
                                selected = SelectedTask(row.label, taskLabel, row.minutes)
                                timeLeft = row.minutes * 60
                                timerRunning = true
                                showCelebrate = false
                            }
                        }
                    )
                    Spacer(modifier = Modifier.height(12.dp))
                }

                if (selected != null) {
                    Spacer(modifier = Modifier.height(14.dp))
                    TimerDisplay(timeLeft, timerRunning)
                }
            }

            if (showCelebrate) {
                Text(
                    text = "🎉 Done! 🎉",
                    fontSize = 36.sp,
                    color = SucculentBrown,
                    modifier = Modifier.alpha(pulseAlpha)
                )
                Spacer(Modifier.height(12.dp))
                Button(
                    onClick = {
                        selected = null
                        showCelebrate = false
                    },
                    colors = ButtonDefaults.buttonColors(containerColor = SucculentBrown)
                ) {
                    Text("Reset", color = Color.White)
                }
            }

            // Centered and not squished Back button
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.Center
            ) {
                Button(
                    onClick = { navController.popBackStack() },
                    colors = ButtonDefaults.buttonColors(containerColor = SucculentButton),
                    modifier = Modifier
                        .widthIn(min = 120.dp)
                        .defaultMinSize(minHeight = 48.dp)
                ) {
                    Text("Back", color = SucculentBrown)
                }
            }
        }
    }
}

data class MicroCommitRow(
    val minutes: Int,
    val label: String,
    val tasks: List<String>
)

data class SelectedTask(
    val rowLabel: String,
    val taskLabel: String,
    val minutes: Int
)

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun MicroCommitRowSelector(
    row: MicroCommitRow,
    selectedTask: SelectedTask?,
    onSelect: (String) -> Unit
) {
    Column(
        modifier = Modifier.fillMaxWidth()
    ) {
        Text(
            row.label,
            color = SucculentBrown,
            fontWeight = FontWeight.Bold,
            fontSize = 15.sp,
            modifier = Modifier.padding(bottom = 2.dp)
        )
        FlowRow(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(10.dp),
            verticalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            row.tasks.forEach { task ->
                val isSelected = selectedTask?.rowLabel == row.label && selectedTask.taskLabel == task
                MicroCommitTaskButton(
                    label = task,
                    selected = isSelected,
                    onClick = { onSelect(task) }
                )
            }
        }
    }
}

@Composable
fun MicroCommitTaskButton(label: String, selected: Boolean, onClick: () -> Unit) {
    val bgColor = if (selected) SucculentButton else SucculentSurface
    val borderColor = if (selected) SucculentBrown else SucculentSurface
    Surface(
        color = bgColor,
        shape = RoundedCornerShape(18.dp),
        modifier = Modifier
            .clickable { onClick() }
            .border(2.dp, borderColor, RoundedCornerShape(18.dp))
    ) {
        Text(
            label,
            color = SucculentBrown,
            fontSize = 13.sp,
            fontWeight = if (selected) FontWeight.Bold else FontWeight.Normal,
            modifier = Modifier.padding(horizontal = 13.dp, vertical = 8.dp)
        )
    }
}

@Composable
fun TimerDisplay(timeLeft: Int, timerRunning: Boolean) {
    val min = timeLeft / 60
    val sec = timeLeft % 60
    val timeString = "%02d:%02d".format(min, sec)
    val textColor = if (timerRunning) SucculentBrown else Color.Gray
    Surface(
        color = SucculentSurface,
        shape = RoundedCornerShape(24.dp),
        modifier = Modifier.padding(top = 12.dp, bottom = 8.dp)
    ) {
        Text(
            text = timeString,
            color = textColor,
            fontSize = 40.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(horizontal = 38.dp, vertical = 12.dp)
        )
    }
}

@Preview(showBackground = true)
@Composable
fun MicroCommitTimerScreenPreview() {
    DoneishAppTheme {
        MicroCommitTimerScreen(navController = rememberNavController())
    }
}
