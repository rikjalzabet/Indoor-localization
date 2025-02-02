package hr.foi.air.core.models.impl

import hr.foi.air.core.models.IAsset

class Asset(
    override var id: Int,
    override val name: String,
    override val x: Float,
    override val y: Float,
    override val lastSync: String,
    override val floorMapId: Int,
    override val active: Boolean
): IAsset {
}