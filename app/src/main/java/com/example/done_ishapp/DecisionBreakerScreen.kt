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
fun DecisionBreakerScreen(navController: NavController) {
    Surface(modifier = Modifier.fillMaxSize()) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(24.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text("Decision Breaker", fontSize = 26.sp)
            Text("This screen will help break analysis paralysis.", fontSize = 16.sp)
        }
    }
}

@Preview(showBackground = true)
@Composable
fun DecisionBreakerPreview() {
    DoneishAppTheme {
        DecisionBreakerScreen(rememberNavController())
    }
}
