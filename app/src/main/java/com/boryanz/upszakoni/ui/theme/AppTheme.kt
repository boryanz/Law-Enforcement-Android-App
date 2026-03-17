package com.boryanz.upszakoni.ui.theme

import androidx.compose.runtime.Composable
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.graphics.Color

data class AppColors(
    val primary: Color,
    val onPrimary: Color,
    val primaryContainer: Color,
    val onPrimaryContainer: Color,
    val background: Color,
    val onBackground: Color,
    val surface: Color,
    val onSurface: Color,
    val outline: Color,
    val outlineVariant: Color,
    val isDark: Boolean,
)

fun lightAppColors() = AppColors(
    primary = Green40,
    onPrimary = Color.White,
    primaryContainer = Green90,
    onPrimaryContainer = Green10,
    background = Neutral99,
    onBackground = Neutral10,
    surface = Neutral99,
    onSurface = Neutral10,
    outline = NeutralVariant50,
    outlineVariant = NeutralVariant90,
    isDark = false,
)

fun darkAppColors() = AppColors(
    primary = Green80,
    onPrimary = Green20,
    primaryContainer = Green30,
    onPrimaryContainer = Green90,
    background = Neutral10,
    onBackground = Neutral90,
    surface = Neutral10,
    onSurface = Neutral90,
    outline = NeutralVariant60,
    outlineVariant = NeutralVariant30,
    isDark = true,
)

val LocalAppColors = staticCompositionLocalOf { lightAppColors() }

object AppTheme {
    val colors: AppColors
        @Composable get() = LocalAppColors.current
}
