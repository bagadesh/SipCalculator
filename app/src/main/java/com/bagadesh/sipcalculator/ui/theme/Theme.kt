package com.bagadesh.sipcalculator.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material.MaterialTheme
import androidx.compose.material.ProvideTextStyle
import androidx.compose.material.darkColors
import androidx.compose.material.lightColors
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import com.google.accompanist.systemuicontroller.rememberSystemUiController

private val DarkColorPalette = darkColors(
    primary = FuturisticPrimary,
    primaryVariant = FuturisticPrimary,
    secondary = FuturisticSecondary,
    background = FuturisticBackground,
    surface = FuturisticSurface,
    onPrimary = FuturisticOnPrimary,
    onSecondary = FuturisticOnSecondary,
    onBackground = FuturisticOnBackground,
    onSurface = FuturisticOnSurface,
    error = FuturisticError
)

// For a futuristic look, we might want to keep the light theme also looking quite sleek, 
// or perhaps force dark mode concepts even in light mode, but for standard practice 
// let's define a clean light version or just map it to the dark one if we want a forced "futuristic dark" look.
// User said "futuristic theme", usually implies dark/neon. Let's make the Light palette also distinctive.
private val LightColorPalette = lightColors(
    primary = Color(0xFF0091EA), // Slightly darker cyan/blue for light mode contrast
    primaryVariant = Color(0xFF0069C0),
    secondary = Color(0xFFAA00FF),
    background = Color(0xFFF5F5F7),
    surface = Color.White,
    onPrimary = Color.White,
    onSecondary = Color.White,
    onBackground = Color(0xFF121212),
    onSurface = Color(0xFF121212)
)

@Composable
fun SipCalculatorTheme(darkTheme: Boolean = isSystemInDarkTheme(), content: @Composable () -> Unit) {
    val systemUiController = rememberSystemUiController()
    
    val colors = if (darkTheme) {
        DarkColorPalette
    } else {
        // You can switch this to DarkColorPalette if you want to force the futuristic dark look always
        LightColorPalette
    }

    // Update system bars to match the theme
    systemUiController.setSystemBarsColor(
        color = colors.background,
        darkIcons = !darkTheme
    )

    MaterialTheme(
        colors = colors,
        typography = Typography,
        shapes = Shapes,
        content = {
            ProvideTextStyle(
                value = TextStyle(color = colors.onSurface),
                content = content
            )
        }
    )
}
