package hr.foi.air.core.models.impl

import hr.foi.air.core.models.IAssetPositionHistory
import java.util.Date

class AssetPositionHistory(
    override val id: Int,
    override val assetId: Int,
    override val x: Float,
    override val y: Float,
    override val dateTime: Date,
    override val floorMapId: Int
) : IAssetPositionHistory{

}