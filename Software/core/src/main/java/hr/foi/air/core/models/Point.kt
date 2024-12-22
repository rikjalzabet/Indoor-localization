package hr.foi.air.core.models

import kotlinx.serialization.Serializable

@Serializable
data class Point(
    val x: Float,
    val y: Float,
    val ordinalNumber: Int
)