package com.example.done_ishapp

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Campaign
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
fun SmartInterruptScreen(navController: NavController) {
    var styleSelected by remember { mutableStateOf("Keep it Light") }
    var deliverySelected by remember { mutableStateOf("Push notification") }

    val styleOptions = listOf(
        "Keep it Light" to Color(0xFFF57C00),
        "Make Me Laugh" to Color(0xFF00796B),
        "Cut to the Chase" to Color(0xFFE65100)
    )

    val deliveryOptions = listOf(
        "Push notification" to Icons.Default.Notifications,
        "Text message" to Icons.Default.Email,
        "Pop-up" to Icons.Filled.Campaign
    )

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            "How Should We Nudge You?",
            fontSize = 24.sp,
            color = Color(0xFFB83B1D)
        )

        styleOptions.forEach { (label, color) ->
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(70.dp)
                    .background(
                        color = if (styleSelected == label) color else color.copy(alpha = 0.5f),
                        shape = RoundedCornerShape(12.dp)
                    )
                    .clickable { styleSelected = label }
                    .padding(16.dp)
            ) {
                Column {
                    Text(label, fontSize = 18.sp, color = Color.White)
                    Text(
                        when (label) {
                            "Keep it Light" -> "Just a gentle reminder."
                            "Make Me Laugh" -> "Hit me with sarcasm"
                            "Cut to the Chase" -> "Be direct, no fluff"
                            else -> ""
                        },
                        fontSize = 12.sp,
                        color = Color.White
                    )
                }
            }
        }

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            deliveryOptions.forEach { (label, icon) ->
                ElevatedButton(
                    onClick = { deliverySelected = label },
                    modifier = Modifier
                        .weight(1f)
                        .height(70.dp),
                    colors = ButtonDefaults.elevatedButtonColors(
                        containerColor = if (deliverySelected == label) Color(0xFFFFA726) else Color(0xFFE0E0E0)
                    )
                ) {
                    Icon(imageVector = icon, contentDescription = label)
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(text = label.split(" ").first())
                }
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        Button(
            onClick = {
                // Save and Continue logic
                navController.navigate("dashboard")
            },
            modifier = Modifier
                .fillMaxWidth()
                .height(50.dp),
            colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFFFA726)),
            shape = RoundedCornerShape(12.dp)
        ) {
            Text("Save & Continue", color = Color.White)
        }
    }
}

@Preview(showBackground = true)
@Composable
fun SmartInterruptPreview() {
    DoneishAppTheme {
        SmartInterruptScreen(navController = rememberNavController())
    }
}
