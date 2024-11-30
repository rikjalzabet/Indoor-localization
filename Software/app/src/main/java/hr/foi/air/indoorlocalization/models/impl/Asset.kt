package hr.foi.air.indoorlocalization.models.impl

import hr.foi.air.indoorlocalization.models.Asset
import java.util.Date

class Asset(
    override val id: Int,
    override val name: String,
    override val x: Float,
    override val y: Float,
    override val lastSync: Date,
    override val floorMapId: Int,
    override val active: Boolean
):Asset {
}