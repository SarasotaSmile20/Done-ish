package com.example.done_ishapp

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.compose.rememberNavController
import com.example.done_ishapp.ui.theme.DoneishAppTheme

@Composable
fun MicroCommitTimerScreen(navController: NavController) {
    Surface(modifier = Modifier.fillMaxSize()) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(24.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text("Micro-Commit Timer", fontSize = 26.sp)
            Text("Set 2, 5, or 15 minute task bursts for gentle action.", fontSize = 16.sp)
        }
    }
}

@Preview(showBackground = true)
@Composable
fun MicroCommitTimerPreview() {
    DoneishAppTheme {
        MicroCommitTimerScreen(rememberNavController())
    }
}
