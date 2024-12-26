package hr.foi.air.core.models.impl

import hr.foi.air.core.models.IAssetZoneHistory
import java.util.Date

class AssetZoneHistory(
    override val id: Int,
    override val assetId: Int,
    override val zoneId: Int,
    override val enterDateTime: Date,
    override val exitDateTime: Date?,
    override val retentionTime: Long
):IAssetZoneHistory {
}