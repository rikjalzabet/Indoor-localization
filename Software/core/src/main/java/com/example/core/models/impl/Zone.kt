package com.example.core.models.impl

import com.example.core.models.IZone
import com.example.core.models.Point
import kotlinx.serialization.Serializable

@Serializable
class Zone(
    override val id: Int,
    override val name: String,
    override val points: List<Point>
): IZone {
}