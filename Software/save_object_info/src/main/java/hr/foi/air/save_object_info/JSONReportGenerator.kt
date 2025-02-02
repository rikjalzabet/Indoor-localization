package hr.foi.air.save_object_info

import android.util.Log
import hr.foi.air.core.models.IAsset
import hr.foi.air.core.models.IAssetPositionHistory
import hr.foi.air.core.models.IAssetZoneHistory
import java.io.File
import org.json.JSONArray
import org.json.JSONObject

class JSONReportGenerator {
    fun saveReport(
        assets: List<IAsset>,
        zoneHistories: List<IAssetZoneHistory>,
        positionHistories: List<IAssetPositionHistory>,
        file: File
    ) {
        val report = JSONObject()
        val assetsArray = JSONArray()
        assets.forEach { asset ->
            val assetObject = JSONObject()
            assetObject.put("id", asset.id)
            assetObject.put("name", asset.name)
            assetObject.put("x", asset.x)
            assetObject.put("y", asset.y)
            assetObject.put("lastSync", asset.lastSync)
            assetObject.put("floorMapId", asset.floorMapId)
            assetObject.put("active", asset.active)
            assetsArray.put(assetObject)
        }
        report.put("assets", assetsArray)

        val zoneHistoriesArray = JSONArray()
        zoneHistories.forEach { history ->
            val historyObject = JSONObject()
            historyObject.put("assetId", history.assetId)
            historyObject.put("zoneId", history.zoneId)
            historyObject.put("enterDateTime", history.enterDateTime)
            historyObject.put("exitDateTime", history.exitDateTime)
            historyObject.put("retentionTime", history.retentionTime)
            zoneHistoriesArray.put(historyObject)
        }
        report.put("zoneHistories", zoneHistoriesArray)

        val positionHistoriesArray = JSONArray()
        positionHistories.forEach { position ->
            val positionObject = JSONObject()
            positionObject.put("assetId", position.assetId)
            positionObject.put("x", position.x)
            positionObject.put("y", position.y)
            positionObject.put("dateTime", position.dateTime)
            positionObject.put("floorMapId", position.floorMapId)
            positionHistoriesArray.put(positionObject)
        }
        report.put("positionHistories", positionHistoriesArray)
        Log.e("JSONReportGenerator", report.toString(4))
        file.writeText(report.toString(4))
    }
}