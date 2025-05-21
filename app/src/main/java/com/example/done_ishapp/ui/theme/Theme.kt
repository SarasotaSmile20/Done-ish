package com.example.done_ishapp.ui.theme

import android.os.Build
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext

private val LightColorScheme = lightColorScheme(
    primary = Terracotta,
    onPrimary = SoftWhite,
    secondary = DeepTeal,
    onSecondary = SoftWhite,
    background = SoftWhite,
    onBackground = Charcoal,
    surface = DesertSand,
    onSurface = Clay
)

private val DarkColorScheme = darkColorScheme(
    primary = Clay,
    onPrimary = SoftWhite,
    secondary = Sage,
    onSecondary = Charcoal,
    background = DarkBackground,
    onBackground = SoftWhite,
    surface = Charcoal,
    onSurface = SoftWhite
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
