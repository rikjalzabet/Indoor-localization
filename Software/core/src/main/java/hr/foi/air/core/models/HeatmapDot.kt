package hr.foi.air.core.models

import androidx.compose.ui.geometry.Offset

data class HeatmapDot(
    val position: Offset,
    var frequency: Int,
    val maxSize: Float = 100f,
    val minSize: Float = 15f,
    val liveMovementSize: Float,
    val maxAlpha: Float = 1f,
    val minAlpha: Float = 0.2f
)

