package hr.foi.air.core.models.impl

import hr.foi.air.core.models.IZone
import hr.foi.air.core.models.Point
import kotlinx.serialization.Serializable

@Serializable
class Zone(
    override var id: Int,
    override val name: String,
    override val points: List<Point>
): IZone {
}