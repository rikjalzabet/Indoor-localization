package hr.foi.air.save_object_info

import android.util.Log
import hr.foi.air.core.models.IAsset
import hr.foi.air.core.models.IAssetPositionHistory
import hr.foi.air.core.models.IAssetZoneHistory
import hr.foi.air.core.models.IZone
import java.io.File
import org.json.JSONArray
import org.json.JSONObject

class JSONReportGenerator : IReportGenerator {
    override fun saveReport(
        assets: List<IAsset>,
        zoneHistories: List<IAssetZoneHistory>,
        positionHistories: List<IAssetPositionHistory>,
        file: File
    ) {
        val reportArray = JSONArray()

        // Generate position histories
        positionHistories.forEach { position ->
            val positionObject = JSONObject()
            positionObject.put("id", position.id)
            positionObject.put("assetId", position.assetId)
            positionObject.put("x", position.x)
            positionObject.put("y", position.y)
            positionObject.put("dateTime", position.dateTime)
            positionObject.put("floorMapId", position.floorMapId)

            val asset = assets.find { it.id == position.assetId }
            asset?.let {
                val assetObject = JSONObject()
                assetObject.put("id", it.id)
                assetObject.put("name", it.name)
                assetObject.put("x", it.x)
                assetObject.put("y", it.y)
                assetObject.put("lastSync", it.lastSync)
                assetObject.put("floorMapId", it.floorMapId)
                assetObject.put("active", it.active)

                // Add floorMap to asset
                val floorMapObject = JSONObject()
                floorMapObject.put("id", it.floorMapId)
                //floorMapObject.put("name", "FloorMap${it.floorMapId}")
                //floorMapObject.put("image", "https://example.com/floorMap${it.floorMapId}.png")
                assetObject.put("floorMap", floorMapObject)

                positionObject.put("asset", assetObject)
            }

            val floorMapObject = JSONObject()
            floorMapObject.put("id", position.floorMapId)
            //floorMapObject.put("name", "FloorMap${position.floorMapId}")
            //floorMapObject.put("image", "https://example.com/floorMap${position.floorMapId}.png")
            positionObject.put("floorMap", floorMapObject)

            reportArray.put(positionObject)
        }

        zoneHistories.forEach { history ->
            val historyObject = JSONObject()
            historyObject.put("id", history.id)
            historyObject.put("assetId", history.assetId)
            historyObject.put("zoneId", history.zoneId)
            historyObject.put("enterDateTime", history.enterDateTime)
            historyObject.put("exitDateTime", history.exitDateTime)
            historyObject.put("retentionTime", history.retentionTime)

            val asset = assets.find { it.id == history.assetId }
            asset?.let {
                val assetObject = JSONObject()
                assetObject.put("id", it.id)
                assetObject.put("name", it.name)
                assetObject.put("x", it.x)
                assetObject.put("y", it.y)
                assetObject.put("lastSync", it.lastSync)
                assetObject.put("floorMapId", it.floorMapId)
                assetObject.put("active", it.active)

                // Add floorMap to asset
                val floorMapObject = JSONObject()
                floorMapObject.put("id", it.floorMapId)
                //floorMapObject.put("name", "FloorMap${it.floorMapId}")
                //floorMapObject.put("image", "https://example.com/floorMap${it.floorMapId}.png")
                assetObject.put("floorMap", floorMapObject)

                historyObject.put("asset", assetObject)
            }

//            val zone = zones.find { it.id == history.zoneId }
//            zone?.let {
//                val zoneObject = JSONObject()
//                zoneObject.put("id", it.id)
//                zoneObject.put("name", it.name)
//
//                val pointsArray = JSONArray(it.points)
//                zoneObject.put("points", pointsArray)
//
//                historyObject.put("zone", zoneObject)
//            }

            reportArray.put(historyObject)
        }

        Log.e("JSONReportGenerator", reportArray.toString(4))
        file.writeText(reportArray.toString(4))
    }
}