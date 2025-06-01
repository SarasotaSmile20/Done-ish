package com.example.done_ishapp

import androidx.compose.animation.core.*
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.Icons
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.flowWithLifecycle
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.NavController
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.compose.rememberNavController
import com.example.done_ishapp.ui.theme.*
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import androidx.compose.runtime.saveable.rememberSaveable

/**
 * A single row of “micro‐commit” options.
 */
data class MicroCommitRow(
    val minutes: Int,
    val label: String,
    val tasks: List<String>
)

/**
 * Holds the row label, the chosen task label, and the minute‐count.
 */
data class SelectedTask(
    val rowLabel: String,
    val taskLabel: String,
    val minutes: Int
)

@OptIn(ExperimentalMaterial3Api::class, ExperimentalLayoutApi::class)
@Composable
fun MicroCommitTimerScreen(navController: NavController) {
    // 1) Define all rows (2‐min, 5‐min, 15‐min)
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

    // 2) UI state: which task is currently selected (or null if none)
    var selectedTask by remember { mutableStateOf<SelectedTask?>(null) }

    // 3) The absolute “end time” in milliseconds. 0L means “no timer running.”
    var endTimeMillis by rememberSaveable { mutableStateOf(0L) }

    // 4) How many seconds remain (recomputed every tick)
    var remainingSeconds by remember { mutableStateOf(0) }

    // 5) Whether to show the celebratory message once it hits zero
    var showCelebrate by remember { mutableStateOf(false) }

    // ---------------------------------------
    //  Lifecycle‐aware ticker (emits once/sec)
    // ---------------------------------------
    val lifecycleOwner = LocalLifecycleOwner.current
    val tickFlow = remember { MutableStateFlow(Unit) }

    LaunchedEffect(Unit) {
        // Only emit ticks while we are in RESUMED state
        lifecycleOwner.lifecycle.repeatOnLifecycle(Lifecycle.State.RESUMED) {
            launch {
                while (true) {
                    tickFlow.emit(Unit)
                    delay(1000L)
                }
            }
        }
    }

    // On each tick, recalc “remainingSeconds” based on endTimeMillis
    LaunchedEffect(tickFlow, endTimeMillis) {
        tickFlow
            .flowWithLifecycle(lifecycleOwner.lifecycle, Lifecycle.State.RESUMED)
            .collect {
                if (endTimeMillis > 0L) {
                    val now = System.currentTimeMillis()
                    val diff = endTimeMillis - now
                    if (diff > 0L) {
                        remainingSeconds = (diff / 1000L).toInt()
                    } else {
                        // Timer hit zero:
                        remainingSeconds = 0
                        endTimeMillis = 0L
                        if (selectedTask != null) {
                            showCelebrate = true
                            // Tell StubbornModeScreen that “canUnlock” is now true:
                            navController.previousBackStackEntry
                                ?.savedStateHandle
                                ?.set<Boolean>("canUnlock", true)
                        }
                    }
                }
            }
    }

    // A simple pulsing alpha animation for the “🎉 Done! 🎉” text
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
                    Text(
                        "The Minimum Effort Club",
                        fontSize = 20.sp,
                        color = SucculentBrown
                    )
                },
                navigationIcon = {
                    IconButton(onClick = {
                        // Always navigate back to Dashboard:
                        navController.navigate("dashboard") {
                            popUpTo("dashboard") { inclusive = false }
                        }
                    }) {
                        Icon(
                            Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = "Back to Dashboard",
                            tint = SucculentBrown
                        )
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = SucculentSurface
                )
            )
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .padding(innerPadding)
                .fillMaxSize()
                .background(SucculentGreen)
                .padding(24.dp)
        ) {
            // -------------------------------------------------
            // Scrollable area: all rows + timer + celebration
            // -------------------------------------------------
            Column(
                modifier = Modifier
                    .weight(1f)
                    .fillMaxWidth()
                    .verticalScroll(rememberScrollState()),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                microCommitOptions.forEach { row ->
                    MicroCommitRowSelector(
                        row = row,
                        selectedTask = selectedTask,
                        onSelect = { taskLabel ->
                            val isSame = (selectedTask?.rowLabel == row.label
                                    && selectedTask?.taskLabel == taskLabel)
                            if (isSame) {
                                //  a) Deselect: stop timer, clear canUnlock
                                selectedTask = null
                                endTimeMillis = 0L
                                showCelebrate = false
                                navController.previousBackStackEntry
                                    ?.savedStateHandle
                                    ?.set<Boolean>("canUnlock", false)
                            } else {
                                // b) New selection: start fresh timer
                                selectedTask = SelectedTask(
                                    rowLabel = row.label,
                                    taskLabel = taskLabel,
                                    minutes = row.minutes
                                )
                                val now = System.currentTimeMillis()
                                endTimeMillis = now + (row.minutes * 60 * 1000L)
                                showCelebrate = false
                                navController.previousBackStackEntry
                                    ?.savedStateHandle
                                    ?.set<Boolean>("canUnlock", false)
                            }
                        }
                    )
                    Spacer(modifier = Modifier.height(12.dp))
                }

                if (selectedTask != null) {
                    Spacer(modifier = Modifier.height(14.dp))
                    TimerDisplay(remainingSeconds, remainingSeconds > 0)
                }

                if (showCelebrate) {
                    Spacer(modifier = Modifier.height(14.dp))
                    Text(
                        text = "🎉 Done! 🎉",
                        fontSize = 36.sp,
                        color = SucculentBrown,
                        modifier = Modifier.alpha(pulseAlpha)
                    )
                    Spacer(modifier = Modifier.height(12.dp))
                    Button(
                        onClick = {
                            // Reset everything after celebrating
                            selectedTask = null
                            remainingSeconds = 0
                            showCelebrate = false
                            navController.previousBackStackEntry
                                ?.savedStateHandle
                                ?.set<Boolean>("canUnlock", false)
                        },
                        colors = ButtonDefaults.buttonColors(containerColor = SucculentBrown)
                    ) {
                        Text("Reset", color = Color.White)
                    }
                }
            }

            // -----------------------------------
            // Bottom row: “Dashboard” + “Back to Stubborn Mode”
            // -----------------------------------
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 12.dp),
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {
                Button(
                    onClick = {
                        navController.navigate("dashboard") {
                            popUpTo("dashboard") { inclusive = false }
                        }
                    },
                    modifier = Modifier
                        .widthIn(min = 120.dp)
                        .height(48.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = SucculentButton)
                ) {
                    Text("Dashboard", color = SucculentBrown)
                }

                Button(
                    onClick = {
                        navController.popBackStack() // Back to Stubborn Mode
                    },
                    modifier = Modifier
                        .widthIn(min = 120.dp)
                        .height(48.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = SucculentButton)
                ) {
                    Text("Back to Stubborn Mode", color = SucculentBrown)
                }
            }
        }
    }
}

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun MicroCommitRowSelector(
    row: MicroCommitRow,
    selectedTask: SelectedTask?,
    onSelect: (String) -> Unit
) {
    Column(modifier = Modifier.fillMaxWidth()) {
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
                val isSelected = (selectedTask?.rowLabel == row.label
                        && selectedTask.taskLabel == task)
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
fun TimerDisplay(timeLeftSeconds: Int, timerRunning: Boolean) {
    val min = timeLeftSeconds / 60
    val sec = timeLeftSeconds % 60
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
fun PreviewMicroCommitTimerScreen() {
    DoneishAppTheme {
        MicroCommitTimerScreen(navController = rememberNavController())
    }
}
