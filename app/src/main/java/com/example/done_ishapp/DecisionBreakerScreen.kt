package com.example.done_ishapp

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.compose.rememberNavController
import com.example.done_ishapp.ui.theme.DoneishAppTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DecisionBreakerScreen(navController: NavController) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Decision Breaker", fontSize = 22.sp) },
                navigationIcon = {
                    IconButton(onClick = { navController.navigate("dashboard") }) {
                        Icon(Icons.Default.Home, contentDescription = "Home")
                    }
                },
                actions = {
                    IconButton(onClick = { /* TODO: Add settings */ }) {
                        Icon(Icons.Default.Settings, contentDescription = "Settings")
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
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                Text(
                    text = "Random low-effort starter actions\nbased on task category.",
                    fontSize = 16.sp,
                    color = Color(0xFFB83B1D),
                    lineHeight = 22.sp
                )

                Spacer(modifier = Modifier.height(32.dp))

                // Grid of Buttons with weight applied here
                Column(verticalArrangement = Arrangement.spacedBy(16.dp)) {
                    Row(horizontalArrangement = Arrangement.spacedBy(16.dp)) {
                        CategoryButton("Housework", Color(0xFFF57C00), modifier = Modifier.weight(1f))
                        CategoryButton("Study", Color(0xFF00796B), modifier = Modifier.weight(1f))
                    }
                    Row(horizontalArrangement = Arrangement.spacedBy(16.dp)) {
                        CategoryButton("Self-Care", Color(0xFFE65100), modifier = Modifier.weight(1f))
                        CategoryButton("Work", Color(0xFFFFCC80), modifier = Modifier.weight(1f))
                    }
                }
            }

            Button(
                onClick = { /* Flip logic */ },
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFFFA726)),
                modifier = Modifier
                    .fillMaxWidth()
                    .height(56.dp),
                shape = RoundedCornerShape(32.dp)
            ) {
                Text("Flip a Coin", color = Color.DarkGray, fontSize = 18.sp)
            }
        }
    }
}

@Composable
fun CategoryButton(label: String, backgroundColor: Color, modifier: Modifier = Modifier) {
    Button(
        onClick = { /* TODO: Add category action */ },
        colors = ButtonDefaults.buttonColors(containerColor = backgroundColor),
        modifier = modifier
            .height(80.dp),
        shape = RoundedCornerShape(12.dp)
    ) {
        Text(label, color = Color.White, fontSize = 16.sp)
    }
}

@Preview(showBackground = true)
@Composable
fun DecisionBreakerPreview() {
    DoneishAppTheme {
        DecisionBreakerScreen(navController = rememberNavController())
    }
}
