package hr.foi.air.indoorlocalization.models.impl

import hr.foi.air.indoorlocalization.models.Point
import hr.foi.air.indoorlocalization.models.IZone
import kotlinx.android.parcel.Parcelize
import kotlinx.serialization.Serializable

@Serializable
class Zone(
    override val id: Int,
    override val name: String,
    override val points: List<Point>
):IZone {
}