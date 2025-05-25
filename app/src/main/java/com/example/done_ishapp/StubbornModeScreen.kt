package com.example.done_ishapp

import androidx.compose.animation.animateColorAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
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
fun StubbornModeScreen(navController: NavController) {
    var isFocusLocked by remember { mutableStateOf(true) }

    val toggleColor by animateColorAsState(
        targetValue = if (isFocusLocked) Color(0xFF4DD0E1) else Color(0xFFB0BEC5),
        label = "ToggleColor"
    )

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFF0288D1))
            .padding(24.dp),
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(Modifier.height(40.dp))

        Text(
            "STUBBORN\nMODE",
            fontSize = 32.sp,
            color = Color.White,
            lineHeight = 36.sp
        )

        Spacer(Modifier.height(24.dp))

        Text(
            "Turn your resistance into accountability. This mode temporarily restricts access to selected distracting apps until you finish a micro-task.",
            fontSize = 16.sp,
            color = Color.White,
            lineHeight = 22.sp
        )

        Spacer(Modifier.height(36.dp))

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text("Focus Lock", fontSize = 18.sp, color = Color.White)
            Switch(
                checked = isFocusLocked,
                onCheckedChange = { isFocusLocked = it },
                colors = SwitchDefaults.colors(
                    checkedThumbColor = Color.White,
                    checkedTrackColor = toggleColor
                )
            )
        }

        Spacer(Modifier.height(24.dp))

        Text("Biometric Verification", fontSize = 18.sp, color = Color.White)

        Spacer(Modifier.height(12.dp))

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(100.dp)
                .background(Color(0xFF01579B), shape = RoundedCornerShape(12.dp))
                .clickable {
                    // Navigate to biometric setup or show prompt
                },
            contentAlignment = Alignment.Center
        ) {
            Text("Enable Fingerprint", color = Color.White, fontSize = 16.sp)
        }

        Spacer(Modifier.height(36.dp))

        Button(
            onClick = {
                // Handle navigation or logic trigger
                navController.popBackStack() // example
            },
            modifier = Modifier
                .fillMaxWidth()
                .height(56.dp),
            shape = RoundedCornerShape(12.dp),
            colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF80DEEA))
        ) {
            Text("AUTO-ACTIVATE MODE", color = Color.White, fontSize = 16.sp)
        }
    }
}

@Preview(showBackground = true)
@Composable
fun StubbornModePreview() {
    DoneishAppTheme {
        StubbornModeScreen(navController = rememberNavController())
    }
}
