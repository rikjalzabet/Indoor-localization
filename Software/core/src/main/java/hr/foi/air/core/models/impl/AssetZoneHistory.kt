package hr.foi.air.core.models.impl

import hr.foi.air.core.models.IAssetZoneHistory
import java.util.Date

class AssetZoneHistory(
    override var id: Int,
    override val assetId: Int,
    override val zoneId: Int,
    override val enterDateTime: String,
    override val exitDateTime: String?,
    override val retentionTime: Long
):IAssetZoneHistory {
}