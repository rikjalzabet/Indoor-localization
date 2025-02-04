package hr.foi.air.core.models

import androidx.compose.ui.geometry.Offset

data class HeatmapDot(
    val position: Offset,
    var frequency: Int,
    //val liveMovementSize: Float,
    val size : Float
)

