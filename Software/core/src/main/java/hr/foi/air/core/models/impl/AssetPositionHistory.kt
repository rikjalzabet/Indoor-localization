package hr.foi.air.core.models.impl

import hr.foi.air.core.models.IAssetPositionHistory

class AssetPositionHistory(
    override var id: Int,
    override val assetId: Int,
    override val x: Float,
    override val y: Float,
    override val dateTime: String,
    override val floorMapId: Int
) : IAssetPositionHistory{

}