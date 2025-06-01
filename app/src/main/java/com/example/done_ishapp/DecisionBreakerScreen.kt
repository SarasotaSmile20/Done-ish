package com.example.done_ishapp

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.automirrored.filled.MenuBook
import androidx.compose.material.icons.filled.AttachMoney
import androidx.compose.material.icons.filled.CleaningServices
import androidx.compose.material.icons.filled.SelfImprovement
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.compose.rememberNavController
import com.example.done_ishapp.ui.theme.DoneishAppTheme
import androidx.compose.ui.draw.clip


// Succulent-inspired green
val SucculentGreen = Color(0xFFB7D6B6) // Adjust for a different shade if desired
val CardBorder = Color(0xFF934F17)
val Orange = Color(0xFFF68B2F)
val Teal = Color(0xFF29807D)
val BurntOrange = Color(0xFFE36B2C)
val Yellow = Color(0xFFF6C572)

data class CategoryTile(val label: String, val icon: ImageVector, val color: Color)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DecisionBreakerScreen(navController: NavController) {
    val categories = listOf(
        CategoryTile("Housework", Icons.Filled.CleaningServices, Orange),
        CategoryTile("Study", Icons.AutoMirrored.Filled.MenuBook, Teal),
        CategoryTile("Self-Care", Icons.Filled.SelfImprovement, BurntOrange),
        CategoryTile("Work", Icons.Filled.AttachMoney, Yellow)
    )

    var selectedCategory by remember { mutableStateOf(categories[0]) }
    var showCoinResult by remember { mutableStateOf(false) }
    var suggestedAction by remember { mutableStateOf("") }

    Scaffold(
        containerColor = SucculentGreen,
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = "Decision Breaker",
                        fontSize = 26.sp,
                        fontWeight = FontWeight.Bold,
                        color = CardBorder
                    )
                },
                navigationIcon = {
                    IconButton(onClick = { navController.navigate("dashboard") }) {
                        Icon(
                            Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = "Back",
                            tint = CardBorder
                        )
                    }
                },
                actions = {
                    IconButton(onClick = { /* TODO: Settings */ }) {
                        Icon(Icons.Default.Settings, contentDescription = "Settings", tint = Teal)
                    }
                }
            )
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .padding(innerPadding)
                .background(SucculentGreen)
                .fillMaxSize()
                .padding(horizontal = 16.dp, vertical = 10.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = "Random low-effort starter actions\nbased on task category.",
                color = CardBorder,
                fontSize = 18.sp,
                fontWeight = FontWeight.Medium,
                modifier = Modifier.padding(vertical = 12.dp),
                lineHeight = 22.sp
            )

            Spacer(modifier = Modifier.height(8.dp))

            // Category buttons in 2 rows
            for (row in 0..1) {
                Row(
                    Modifier
                        .fillMaxWidth()
                        .padding(vertical = 4.dp),
                    horizontalArrangement = Arrangement.SpaceEvenly
                ) {
                    for (col in 0..1) {
                        val index = row * 2 + col
                        if (index < categories.size) {
                            CategoryButton(
                                category = categories[index],
                                selected = selectedCategory == categories[index],
                                modifier = Modifier.weight(1f),
                                onClick = {
                                    selectedCategory = categories[index]
                                    showCoinResult = false
                                }
                            )
                            if (col == 0) Spacer(modifier = Modifier.width(16.dp))
                        }
                    }
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            Button(
                onClick = {
                    val randomCategory = categories.random()
                    selectedCategory = randomCategory
                    suggestedAction = getSuggestedAction(randomCategory.label)
                    showCoinResult = true
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(58.dp)
                    .padding(horizontal = 8.dp),
                shape = RoundedCornerShape(24.dp),
                colors = ButtonDefaults.buttonColors(containerColor = Orange)
            ) {
                Text(text = "Flip a Coin", fontWeight = FontWeight.Bold, fontSize = 20.sp)
            }

            Spacer(modifier = Modifier.height(16.dp))

            Button(
                onClick = {
                    suggestedAction = getSuggestedAction(selectedCategory.label)
                    showCoinResult = true
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(50.dp),
                shape = RoundedCornerShape(16.dp),
                colors = ButtonDefaults.buttonColors(containerColor = Teal)
            ) {
                Text("Get Starter Action", color = Color.White, fontWeight = FontWeight.Bold)
            }

            Spacer(modifier = Modifier.height(24.dp))

            if (showCoinResult && suggestedAction.isNotEmpty()) {
                Text("Suggested action:", fontSize = 15.sp, color = CardBorder)
                Spacer(modifier = Modifier.height(8.dp))
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .border(2.dp, CardBorder, RoundedCornerShape(14.dp))
                        .background(Color.White, RoundedCornerShape(14.dp))
                        .padding(16.dp)
                ) {
                    Text(suggestedAction, fontSize = 18.sp, color = CardBorder)
                }
            }
        }
    }
}

@Composable
fun CategoryButton(
    category: CategoryTile,
    selected: Boolean,
    modifier: Modifier = Modifier,
    onClick: () -> Unit
) {
    val borderColor = if (selected) Color(0xFF934F17) else CardBorder
    Box(
        modifier = modifier
            .aspectRatio(1.1f)
            .padding(8.dp)
            .clip(RoundedCornerShape(22.dp))
            .background(category.color)
            .border(3.dp, borderColor, RoundedCornerShape(22.dp))
            .clickable { onClick() },
        contentAlignment = Alignment.Center
    ) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Icon(
                imageVector = category.icon,
                contentDescription = category.label,
                tint = Color.White,
                modifier = Modifier.size(48.dp)
            )
            Spacer(modifier = Modifier.height(12.dp))
            Text(
                text = category.label,
                color = Color.White,
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold
            )
        }
    }
}

// Updated utility: Five random actions for each category
fun getSuggestedAction(category: String): String = when (category) {
    "Housework" -> listOf(
        "Pick up one item from the floor.",
        "Wipe a single surface.",
        "Put away five things.",
        "Start the laundry.",
        "Empty the trash in one room."
    ).random()
    "Study" -> listOf(
        "Open one tab to your course page.",
        "Skim the chapter headings.",
        "Write a single flashcard.",
        "Read the first paragraph of your assignment.",
        "Highlight one important sentence."
    ).random()
    "Self-Care" -> listOf(
        "Stretch for 30 seconds.",
        "Drink a glass of water.",
        "Take three deep breaths.",
        "Put lotion on your hands.",
        "Stand outside for one minute."
    ).random()
    "Work" -> listOf(
        "Write one sentence of your task.",
        "Send one follow-up email.",
        "Make a two-item checklist.",
        "Organize your desktop.",
        "File one document."
    ).random()
    else -> "Take a deep breath and smile."
}

@Preview(showBackground = true)
@Composable
fun DecisionBreakerPreview() {
    DoneishAppTheme {
        DecisionBreakerScreen(navController = rememberNavController())
    }
}
