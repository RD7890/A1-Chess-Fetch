package com.rohan.chessfetch.ui.theme

import android.app.Activity
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalView
import androidx.core.view.WindowCompat

private val ChessColorScheme = darkColorScheme(
    primary             = Gold,
    onPrimary           = Color.Black,
    primaryContainer    = GoldDim,
    onPrimaryContainer  = GoldLight,
    secondary           = OnSurfaceMedium,
    onSecondary         = Color.Black,
    tertiary            = BlitzColor,
    background          = Background,
    onBackground        = OnSurface,
    surface             = Surface,
    onSurface           = OnSurface,
    surfaceVariant      = SurfaceContainer,
    onSurfaceVariant    = OnSurfaceMedium,
    outline             = BorderColor,
    outlineVariant      = DividerColor,
    error               = LossRed,
    onError             = Color.White,
    inverseSurface      = OnSurface,
    inverseOnSurface    = Background,
    scrim               = Color.Black
)

@Composable
fun ChessFetchTheme(content: @Composable () -> Unit) {
    val view = LocalView.current
    if (!view.isInEditMode) {
        SideEffect {
            val window = (view.context as Activity).window
            window.statusBarColor = Background.toArgb()
            window.navigationBarColor = Background.toArgb()
            WindowCompat.getInsetsController(window, view).apply {
                isAppearanceLightStatusBars = false
                isAppearanceLightNavigationBars = false
            }
        }
    }
    MaterialTheme(
        colorScheme = ChessColorScheme,
        typography  = ChessFetchTypography,
        content     = content
    )
}
