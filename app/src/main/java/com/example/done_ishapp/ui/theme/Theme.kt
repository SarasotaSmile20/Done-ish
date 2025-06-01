package com.example.done_ishapp.ui.theme

import android.os.Build
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.graphics.Color // <-- Add this import

// Import your palette colors from Color.kt
import com.example.done_ishapp.ui.theme.SucculentGreen
import com.example.done_ishapp.ui.theme.SucculentBrown
import com.example.done_ishapp.ui.theme.SucculentButton
import com.example.done_ishapp.ui.theme.SucculentSurface
import com.example.done_ishapp.ui.theme.SucculentOnBackground

private val LightColorScheme = lightColorScheme(
    primary = SucculentBrown,
    onPrimary = Color.White,
    secondary = SucculentButton,
    onSecondary = SucculentBrown,
    background = SucculentGreen,
    onBackground = SucculentOnBackground,
    surface = SucculentSurface,
    onSurface = SucculentBrown
)

private val DarkColorScheme = darkColorScheme(
    primary = SucculentButton,
    onPrimary = Color.Black,
    secondary = SucculentGreen,
    onSecondary = Color.Black,
    background = Color(0xFF32463E),       // Muted dark green for dark mode
    onBackground = Color.White,
    surface = Color(0xFF25372F),          // Deeper green card for dark mode
    onSurface = SucculentButton
)

@Composable
fun DoneishAppTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    dynamicColor: Boolean = false,
    content: @Composable () -> Unit
) {
    val colorScheme = when {
        dynamicColor && Build.VERSION.SDK_INT >= Build.VERSION_CODES.S -> {
            val context = LocalContext.current
            if (darkTheme) dynamicDarkColorScheme(context) else dynamicLightColorScheme(context)
        }
        darkTheme -> DarkColorScheme
        else -> LightColorScheme
    }

    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography,
        content = content
    )
}
