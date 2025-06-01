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

@Composable
fun StubbornModeScreen(navController: NavController) {
    var isFocusLocked by remember { mutableStateOf(false) }
    val appList = listOf("Instagram", "TikTok", "YouTube", "Facebook", "Gmail")
    var restrictedApps by remember { mutableStateOf(setOf<String>()) }
    var showUnlockedDialog by remember { mutableStateOf(false) }

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
            "Turn your resistance into accountability. This mode temporarily restricts access to selected distracting apps until you complete a Micro Commitment.",
            fontSize = 16.sp,
            color = SucculentOnBackground,
            lineHeight = 22.sp
        )

        Spacer(Modifier.height(36.dp))

        // Focus Lock Toggle: can only be toggled ON by user, NOT OFF (must use fingerprint)
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
                    }
                    // Do nothing if trying to turn off while locked
                },
                enabled = !isFocusLocked, // Can't toggle OFF
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
            Spacer(Modifier.height(22.dp))
        } else {
            // Only show biometric (Micro Commitment) label when Focus Lock is ON
            Text(
                "Micro Commitment Required",
                fontSize = 18.sp,
                color = SucculentBrown,
                fontWeight = FontWeight.Bold
            )
            Spacer(Modifier.height(10.dp))
        }

        // Biometric Verification (fingerprint icon and spaced label)
        Text("Biometric Verification", fontSize = 18.sp, color = SucculentBrown)
        Spacer(Modifier.height(12.dp))

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(100.dp)
                .background(
                    if (isFocusLocked) SucculentButton else SucculentSurface,
                    shape = RoundedCornerShape(12.dp)
                )
                .clickable(
                    enabled = isFocusLocked
                ) {
                    if (isFocusLocked) {
                        isFocusLocked = false
                        showUnlockedDialog = true
                    }
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
                        .alpha(if (isFocusLocked) 1f else 0.4f),
                    tint = SucculentBrown
                )
                Spacer(modifier = Modifier.height(18.dp)) // Space between icon and text
                Text(
                    if (isFocusLocked) "Tap to unlock" else "Fingerprint ready",
                    color = SucculentBrown,
                    fontSize = 12.sp
                )
            }
        }

        if (isFocusLocked) {
            Spacer(Modifier.height(12.dp))
            Text(
                "Complete a Micro Commitment to disable Focus Lock and access your restricted apps.",
                color = SucculentBrown,
                fontSize = 14.sp
            )
        }

        Spacer(Modifier.height(32.dp))

        Button(
            onClick = {
                navController.popBackStack()
            },
            modifier = Modifier
                .fillMaxWidth()
                .height(56.dp),
            shape = RoundedCornerShape(12.dp),
            colors = ButtonDefaults.buttonColors(containerColor = SucculentBrown)
        ) {
            Text("Back", color = Color.White, fontSize = 16.sp)
        }
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
