package com.example.done_ishapp

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.fontResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.done_ishapp.ui.theme.DoneishAppTheme
import com.example.done_ishapp.R

@Composable
fun DoneishLoginScreen(navController: NavController) {
    val cursiveFont = FontFamily(Font(R.font.cedarvillecursive_regular))
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var showDisclaimerDialog by remember { mutableStateOf(false) }

    Box(modifier = Modifier.fillMaxSize()) {
        // Background image
        Image(
            painter = painterResource(id = R.drawable.succulents),
            contentDescription = "Background Succulents",
            modifier = Modifier.fillMaxSize(),
            contentScale = ContentScale.Crop
        )

        // Foreground content
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 32.dp, vertical = 64.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Top
        ) {
            Text(
                text = "Done-ish",
                style = TextStyle(
                    fontFamily = cursiveFont,
                    fontSize = 50.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.Black
                )
            )

            Spacer(modifier = Modifier.height(8.dp))

            Text(
                text = "\"Because you want to change the world...\nbut someone still has to change the toilet paper.\"",
                style = MaterialTheme.typography.bodyMedium.copy(
                    fontStyle = FontStyle.Italic,
                    color = Color.DarkGray,
                    fontSize = 14.sp
                ),
                textAlign = TextAlign.Center
            )

            Spacer(modifier = Modifier.height(32.dp))

            OutlinedTextField(
                value = email,
                onValueChange = { email = it },
                label = { Text("Email", color = Color.DarkGray) },
                textStyle = TextStyle(color = Color.DarkGray),
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(16.dp))

            OutlinedTextField(
                value = password,
                onValueChange = { password = it },
                label = { Text("Password", color = Color.DarkGray) },
                textStyle = TextStyle(color = Color.DarkGray),
                visualTransformation = PasswordVisualTransformation(),
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(24.dp))

            Button(
                onClick = {
                    showDisclaimerDialog = true
                },
                modifier = Modifier.fillMaxWidth(),
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFFFA726))
            ) {
                Text("Log in", color = Color.White)
            }
        }

        // Disclaimer Dialog
        if (showDisclaimerDialog) {
            AlertDialog(
                onDismissRequest = { /* Prevent dismissal by tapping outside */ },
                title = {
                    Text("Disclaimer", fontWeight = FontWeight.Bold)
                },
                text = {
                    Column {
                        Text("• Done-ish is intended as a productivity and habit-formation tool, not a substitute for professional mental health or medical advice.")
                        Spacer(modifier = Modifier.height(4.dp))
                        Text("• Results may vary; individual experiences depend on personal motivation and context.")
                        Spacer(modifier = Modifier.height(4.dp))
                        Text("• Always consult a qualified professional for serious time-management or mental health concerns.")
                    }
                },
                confirmButton = {
                    TextButton(
                        onClick = {
                            showDisclaimerDialog = false
                            navController.navigate("dashboard") {
                                popUpTo("login") { inclusive = true }
                            }
                        }
                    ) {
                        Text("Proceed")
                    }
                },
                dismissButton = {
                    TextButton(
                        onClick = {
                            showDisclaimerDialog = false
                        }
                    ) {
                        Text("Cancel")
                    }
                }
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun DoneishLoginScreenPreview() {
    DoneishAppTheme {
        DoneishLoginScreen(navController = rememberNavController())
    }
}
