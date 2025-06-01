package com.example.done_ishapp

import androidx.compose.animation.animateColorAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Fingerprint
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
import androidx.compose.runtime.saveable.rememberSaveable

@Composable
fun StubbornModeScreen(navController: NavController) {
    // Read “canUnlock” from the savedStateHandle of the previous backStackEntry (if any).
    // But once set(false), we only set true from MicroCommitTimerScreen via previousBackStackEntry.
    val canUnlock = navController.currentBackStackEntry
        ?.savedStateHandle
        ?.get<Boolean>("canUnlock") ?: false

    // Lift “isFocusLocked” into a singleton so it survives all navigations:
    var isFocusLocked by rememberSaveable { mutableStateOf(AppState.isFocusLocked) }

    // Whenever we toggle, sync to AppState
    LaunchedEffect(isFocusLocked) {
        AppState.isFocusLocked = isFocusLocked
    }

    // List of apps user can restrict
    var restrictedApps by rememberSaveable { mutableStateOf(setOf<String>()) }
    var showUnlockedDialog by remember { mutableStateOf(false) }

    val appList = listOf("Instagram", "TikTok", "YouTube", "Facebook", "Gmail")

    val toggleColor by animateColorAsState(
        targetValue = if (isFocusLocked) SucculentButton else SucculentBrown.copy(alpha = 0.3f),
        label = "ToggleColor"
    )

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(SucculentGreen)
            .verticalScroll(rememberScrollState())
            .padding(24.dp),
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(Modifier.height(40.dp))

        Text(
            "STUBBORN\nMODE",
            fontSize = 32.sp,
            color = SucculentBrown,
            lineHeight = 36.sp,
            fontWeight = FontWeight.Bold
        )

        Spacer(Modifier.height(24.dp))

        Text(
            "Turn your resistance into accountability. Once Focus Lock is enabled, your chosen apps remain locked until you complete a Micro Commitment and return here to unlock.",
            fontSize = 16.sp,
            color = SucculentOnBackground,
            lineHeight = 22.sp,
            modifier = Modifier.padding(bottom = 24.dp)
        )

        // Focus Lock Toggle
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text("Focus Lock", fontSize = 18.sp, color = SucculentBrown)
            Switch(
                checked = isFocusLocked,
                onCheckedChange = { isChecked ->
                    if (!isFocusLocked && isChecked) {
                        isFocusLocked = true
                        // Clear any previous “canUnlock” when turning lock ON:
                        navController.currentBackStackEntry
                            ?.savedStateHandle
                            ?.set("canUnlock", false)
                    }
                    // Do nothing if trying to turn off while locked
                },
                colors = SwitchDefaults.colors(
                    checkedThumbColor = SucculentBrown,
                    checkedTrackColor = toggleColor
                )
            )
        }

        Spacer(Modifier.height(20.dp))

        if (!isFocusLocked) {
            // App selection UI when Focus Lock is OFF
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(SucculentSurface, RoundedCornerShape(12.dp))
                    .padding(14.dp)
            ) {
                Text(
                    "Select apps to restrict:",
                    color = SucculentBrown,
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Medium
                )
                Spacer(Modifier.height(6.dp))
                appList.forEach { app ->
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier
                            .fillMaxWidth()
                            .clickable {
                                restrictedApps = if (restrictedApps.contains(app))
                                    restrictedApps - app else restrictedApps + app
                            }
                            .padding(vertical = 4.dp)
                    ) {
                        Checkbox(
                            checked = restrictedApps.contains(app),
                            onCheckedChange = {
                                restrictedApps = if (it) restrictedApps + app else restrictedApps - app
                            },
                            colors = CheckboxDefaults.colors(
                                checkedColor = SucculentBrown,
                                uncheckedColor = SucculentButton
                            )
                        )
                        Text(app, color = SucculentBrown, fontSize = 15.sp)
                    }
                }
            }
        } else {
            // Once Focus Lock is ON:
            Spacer(Modifier.height(12.dp))
            Text(
                "Your selected apps are now locked. To unlock, complete a Micro Commitment next.",
                fontSize = 14.sp,
                color = SucculentBrown,
                modifier = Modifier.padding(bottom = 24.dp)
            )

            // “Go to Micro-Commit” button (always visible if isFocusLocked == true)
            Button(
                onClick = { navController.navigate("microcommit") },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(50.dp),
                shape = RoundedCornerShape(16.dp),
                colors = ButtonDefaults.buttonColors(containerColor = SucculentButton)
            ) {
                Text("Go to Micro-Commit", color = SucculentBrown)
            }

            Spacer(Modifier.height(24.dp))

            // Biometric box only becomes tappable if canUnlock == true
            Text("Biometric Verification", fontSize = 18.sp, color = SucculentBrown)
            Spacer(Modifier.height(12.dp))

            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(100.dp)
                    .background(
                        if (canUnlock) SucculentButton else SucculentSurface,
                        shape = RoundedCornerShape(12.dp)
                    )
                    .clickable(
                        enabled = canUnlock
                    ) {
                        isFocusLocked = false
                        AppState.isFocusLocked = false  // also update global
                        showUnlockedDialog = true
                    },
                contentAlignment = Alignment.Center
            ) {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center
                ) {
                    Icon(
                        imageVector = Icons.Filled.Fingerprint,
                        contentDescription = "Fingerprint",
                        modifier = Modifier
                            .size(60.dp)
                            .alpha(if (canUnlock) 1f else 0.4f),
                        tint = SucculentBrown
                    )
                    Spacer(modifier = Modifier.height(18.dp))
                    Text(
                        if (canUnlock) "Tap to unlock" else "Locked",
                        color = SucculentBrown,
                        fontSize = 12.sp
                    )
                }
            }
        }

        Spacer(Modifier.weight(1f)) // push the bottom button to the bottom

        // “Back to Dashboard” button (when NOT locked) or simply “Back” (could be repurposed):
        Button(
            onClick = {
                // If locked, we still navigate to Dashboard but preserve lock state in AppState
                navController.navigate("dashboard") {
                    popUpTo("dashboard") { inclusive = false }
                }
            },
            modifier = Modifier
                .fillMaxWidth()
                .height(56.dp),
            shape = RoundedCornerShape(12.dp),
            colors = ButtonDefaults.buttonColors(containerColor = SucculentBrown)
        ) {
            Text("Back to Dashboard", color = Color.White, fontSize = 16.sp)
        }

        Spacer(Modifier.height(12.dp))
    }

    // Unlock confirmation dialog
    if (showUnlockedDialog) {
        AlertDialog(
            onDismissRequest = { showUnlockedDialog = false },
            confirmButton = {
                TextButton(onClick = { showUnlockedDialog = false }) {
                    Text("OK", color = SucculentBrown)
                }
            },
            title = { Text("Focus Lock Disabled", color = SucculentBrown) },
            text = {
                Text(
                    "You’ve completed a Micro Commitment! Focus Lock is now off and your restricted apps are available.",
                    color = SucculentOnBackground
                )
            }
        )
    }
}

@Preview(showBackground = true)
@Composable
fun StubbornModePreview() {
    DoneishAppTheme {
        StubbornModeScreen(navController = rememberNavController())
    }
}
