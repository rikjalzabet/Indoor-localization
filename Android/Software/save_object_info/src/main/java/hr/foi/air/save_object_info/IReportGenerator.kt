package hr.foi.air.save_object_info

import hr.foi.air.core.models.IAsset
import hr.foi.air.core.models.IAssetPositionHistory
import hr.foi.air.core.models.IAssetZoneHistory
import hr.foi.air.core.models.IZone
import java.io.File

interface IReportGenerator {
    fun saveReport(
        assets: List<IAsset>,
        zoneHistories: List<IAssetZoneHistory>,
        positionHistories: List<IAssetPositionHistory>,
        file: File
    )
}