package hr.foi.air.indoorlocalization.helpers

import androidx.compose.ui.graphics.Color
import hr.foi.air.core.models.HeatmapDot

fun calculateColorForFrequency(frequency: Int): Color {
    return when {
        frequency < 10 -> Color.Blue.copy(alpha = 0.5f)
        frequency < 20 -> Color.Green.copy(alpha = 0.5f)
        frequency < 30 -> Color.Yellow.copy(alpha = 0.5f)
        else -> Color.Red.copy(alpha = 0.5f)
    }
}

fun calculateSizeForFrequency(frequency: Int): Float {
    return 35f - (frequency * 2).coerceIn(5, 30)
}

fun calculateSizeForColor(color: Color, dot: HeatmapDot): Float {
    return when (color) {
        Color.Blue -> dot.maxSize
        Color.Green -> dot.maxSize-10
        Color.Yellow -> dot.minSize
        else -> dot.liveMovementSize
    }
}