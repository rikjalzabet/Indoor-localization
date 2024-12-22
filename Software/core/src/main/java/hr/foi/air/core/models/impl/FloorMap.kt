package hr.foi.air.core.models.impl

import hr.foi.air.core.models.IFloorMap

class FloorMap(
    override val id: Int,
    override val name: String,
    override val image: String
): IFloorMap {
}