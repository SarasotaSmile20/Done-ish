package com.example.done_ishapp

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.foundation.selection.toggleable
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Campaign
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.compose.rememberNavController
import com.example.done_ishapp.ui.theme.*

@Composable
fun SmartInterruptScreen(navController: NavController) {
    var styleSelected by remember { mutableStateOf("Keep it Light") }
    var deliverySelected by remember { mutableStateOf("Push notification") }
    var frequencySelected by remember { mutableStateOf("Sometimes") }
    val appList = listOf("Gmail", "Slack", "YouTube", "TikTok", "Facebook")
    var selectedApps by remember { mutableStateOf(setOf<String>()) }
    var showConfirmation by remember { mutableStateOf(false) }

    val styleOptions = listOf(
        "Keep it Light" to SucculentButton,
        "Make Me Laugh" to SucculentBrown,
        "Cut to the Chase" to Color(0xFF8E562E)
    )

    val styleDescriptions = mapOf(
        "Keep it Light" to "Just a gentle, positive nudge.",
        "Make Me Laugh" to "Motivate me with a little sarcasm.",
        "Cut to the Chase" to "No fluff, just get me moving!"
    )

    val deliveryOptions = listOf(
        "Push notification" to Icons.Default.Notifications,
        "Text message" to Icons.Default.Email,
        "Pop-up" to Icons.Filled.Campaign
    )

    val frequencyOptions = listOf("Rarely", "Sometimes", "Frequently")

    Column(
        modifier = Modifier
            .verticalScroll(rememberScrollState())
            .fillMaxSize()
            .background(SucculentGreen)
            .padding(20.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            "How Should We Nudge You?",
            fontSize = 24.sp,
            color = SucculentBrown,
            fontWeight = FontWeight.Bold
        )

        // Tone Style Options
        Column(
            modifier = Modifier.fillMaxWidth(),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            styleOptions.forEach { (label, color) ->
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(
                            color = if (styleSelected == label) color else color.copy(alpha = 0.4f),
                            shape = RoundedCornerShape(16.dp)
                        )
                        .clickable { styleSelected = label }
                        .padding(vertical = 10.dp, horizontal = 16.dp)
                ) {
                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        Text(
                            label,
                            fontSize = 18.sp,
                            color = Color.White,
                            fontWeight = if (styleSelected == label) FontWeight.Bold else FontWeight.Normal
                        )
                        Spacer(modifier = Modifier.height(2.dp))
                        Text(
                            styleDescriptions[label] ?: "",
                            fontSize = 13.sp,
                            color = Color.White
                        )
                    }
                }
            }
        }

        // Delivery method icons and labels (icon above text)
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 6.dp),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            deliveryOptions.forEach { (label, icon) ->
                Column(
                    modifier = Modifier
                        .weight(1f)
                        .clickable { deliverySelected = label }
                        .padding(vertical = 4.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Box(
                        modifier = Modifier
                            .size(46.dp)
                            .clip(CircleShape)
                            .background(if (deliverySelected == label) SucculentBrown else SucculentButton)
                            .padding(8.dp),
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(
                            imageVector = icon,
                            contentDescription = label,
                            tint = Color.White,
                            modifier = Modifier.size(28.dp)
                        )
                    }
                    Spacer(modifier = Modifier.height(2.dp))
                    Text(
                        label.split(" ").joinToString(" "),
                        color = if (deliverySelected == label) SucculentBrown else SucculentBrown.copy(alpha = 0.6f),
                        fontSize = 12.sp
                    )
                }
            }
        }

        // Frequency dropdown
        Spacer(modifier = Modifier.height(8.dp))
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("How often to interrupt:", color = SucculentBrown, fontSize = 16.sp)
            Spacer(modifier = Modifier.width(12.dp))
            var expanded by remember { mutableStateOf(false) }
            Box {
                Button(
                    onClick = { expanded = true },
                    colors = ButtonDefaults.buttonColors(containerColor = SucculentButton),
                    shape = RoundedCornerShape(10.dp),
                    contentPadding = PaddingValues(horizontal = 16.dp, vertical = 4.dp)
                ) {
                    Text(frequencySelected, color = SucculentBrown, fontSize = 14.sp)
                }
                DropdownMenu(
                    expanded = expanded,
                    onDismissRequest = { expanded = false }
                ) {
                    frequencyOptions.forEach { freq ->
                        DropdownMenuItem(
                            text = { Text(freq, color = SucculentBrown) },
                            onClick = {
                                frequencySelected = freq
                                expanded = false
                            }
                        )
                    }
                }
            }
        }

        // App picker
        Spacer(modifier = Modifier.height(8.dp))
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .background(SucculentSurface, RoundedCornerShape(12.dp))
                .padding(10.dp)
        ) {
            Text("Apps to trigger Smart Interrupt:", color = SucculentBrown, fontSize = 16.sp)
            Spacer(modifier = Modifier.height(6.dp))
            appList.forEach { app ->
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier
                        .fillMaxWidth()
                        .clickable {
                            selectedApps = if (selectedApps.contains(app))
                                selectedApps - app else selectedApps + app
                        }
                        .padding(vertical = 2.dp)
                ) {
                    Checkbox(
                        checked = selectedApps.contains(app),
                        onCheckedChange = {
                            selectedApps = if (it) selectedApps + app else selectedApps - app
                        },
                        colors = CheckboxDefaults.colors(
                            checkedColor = SucculentBrown,
                            uncheckedColor = SucculentButton
                        )
                    )
                    Text(app, color = SucculentBrown, fontSize = 14.sp)
                }
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Save & Continue button (always visible)
        Button(
            onClick = { showConfirmation = true },
            modifier = Modifier
                .fillMaxWidth()
                .height(50.dp),
            colors = ButtonDefaults.buttonColors(containerColor = SucculentBrown),
            shape = RoundedCornerShape(12.dp)
        ) {
            Text("Save & Continue", color = Color.White)
        }
    }

    // Confirmation dialog
    if (showConfirmation) {
        AlertDialog(
            onDismissRequest = { showConfirmation = false },
            confirmButton = {
                TextButton(onClick = {
                    showConfirmation = false
                    navController.navigate("dashboard")
                }) {
                    Text("OK", color = SucculentBrown)
                }
            },
            title = { Text("Smart Interrupt Setup Complete!", color = SucculentBrown) },
            text = {
                Text(
                    "Your preferences are saved. Smart Interrupts will now help keep you on track!",
                    color = SucculentOnBackground
                )
            }
        )
    }
}

@Preview(showBackground = true)
@Composable
fun SmartInterruptPreview() {
    DoneishAppTheme {
        SmartInterruptScreen(navController = rememberNavController())
    }
}
