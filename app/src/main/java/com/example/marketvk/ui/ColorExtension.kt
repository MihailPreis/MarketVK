package com.example.marketvk.ui

import androidx.compose.ui.graphics.Color
import kotlin.math.abs


val Color.isDark: Boolean
    get() = 0.2126 * red + 0.7152 * green + 0.0722 * blue < 0.50

fun Color.Companion.gen(text: String, alpha: Float): Color {
    val finalHash = abs(text.hashCode()) % (256 * 256 * 256)
    return Color(
        red = ((finalHash and 0xFF0000) shr 16) / 255f,
        green = ((finalHash and 0xFF00) shr 8) / 255f,
        blue = (finalHash and 0xFF) / 255f,
        alpha = alpha
    )
}

fun Color.Companion.gen(text: String, isForeground: Boolean, alpha: Float = 1f): Color = when {
    isForeground -> when {
        Color.gen(text, alpha).isDark -> White
        else -> Black
    }

    else -> Color.gen(text, alpha)
}