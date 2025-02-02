package hr.foi.air.indoorlocalization.parser

import android.util.Log
import hr.foi.air.core.models.impl.FloorMap
import hr.foi.air.core.models.impl.Zone
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.google.gson.reflect.TypeToken
import hr.foi.air.core.models.impl.Asset
import hr.foi.air.core.models.impl.AssetPositionHistory
import hr.foi.air.core.models.impl.AssetZoneHistory
import hr.foi.air.core.parser.DataParser
import hr.foi.air.core.parser.*
import kotlinx.serialization.json.*
import java.util.UUID


class JsonDataParser(): DataParser {
    override fun updateZones(data: String) {
        val gson = Gson()
        val newZones = gson.fromJson(data, Array<Zone>::class.java).toList()
        newZones.forEach { newZone ->
            if (newZone.id == 0) {
                newZone.id = UUID.randomUUID().hashCode()
            }
            if (zonesList.none { it.id == newZone.id }) {
                zonesList.add(newZone)
            }
        }
    }

    override fun updateFloorMaps(data: String) {
        val gson = Gson()
        val newFloorMaps = gson.fromJson(data, Array<FloorMap>::class.java).toList()
        newFloorMaps.forEach { newFloorMap ->
            if (newFloorMap.id == 0) {
                newFloorMap.id = UUID.randomUUID().hashCode()
            }
            if (floorMapList.none { it.id == newFloorMap.id }) {
                floorMapList.add(newFloorMap)
            }
        }
    }

    override fun updateLiveAssetPositions(data: String) {
        val gson: Gson = GsonBuilder()
            //.setDateFormat("yyyy-MM-dd HH:mm:ss")
            .create()
        val assets: List<Asset> = gson.fromJson(data, object : TypeToken<List<Asset>>() {}.type)
        Log.d("JsonDataParser", "Parsed ${assets.size} assets")
        assets.forEach { newAsset ->
            if (newAsset.id == 0) {
                newAsset.id = UUID.randomUUID().hashCode()
            }
            Log.d("JsonDataParserAB", "Asset: ${newAsset.id}, x = ${newAsset.x}, y = ${newAsset.y}")
            if (liveAssetPositionList.none { it.id == newAsset.id }) {
                liveAssetPositionList.add(newAsset)
            }
        }
        Log.i("JsonDataParser", "Updated live asset positions has ${liveAssetPositionList.size} assets")
    }

    fun updateAssetPositionHistory(data: String) {
        val gson: Gson = GsonBuilder()
            //.setDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'")
            .create()
        val assetPositionHistory: List<AssetPositionHistory> = gson.fromJson(data, object : TypeToken<List<AssetPositionHistory>>() {}.type)
        assetPositionHistory.forEach { newHistory ->
            if (newHistory.id == 0) {
                newHistory.id = UUID.randomUUID().hashCode()
            }
            if (assetPositionHistoryList.none { it.id == newHistory.id }) {
                assetPositionHistoryList.add(newHistory)
                Log.i("YIPPI123","$assetPositionHistoryList")
            }
        }
        Log.i("JsonDataParser", "Updated asset position history has ${assetPositionHistoryList.size} entries")
    }

    fun updateAssetZoneHistory(data: String) {
        val gson: Gson = GsonBuilder()
            //.setDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'")
            .create()
        val assetZoneHistory: List<AssetZoneHistory> = gson.fromJson(data, object : TypeToken<List<AssetZoneHistory>>() {}.type)
        assetZoneHistory.forEach { newHistory ->
            if (newHistory.id == 0) {
                newHistory.id = UUID.randomUUID().hashCode()
            }
            if (assetZoneHistoryList.none { it.id == newHistory.id }) {
                assetZoneHistoryList.add(newHistory)
            }
        }
        Log.i("JsonDataParser", "Updated asset zone history has ${assetZoneHistoryList.size} entries")
    }

    ////////////////////////////////////////////////////////

    /*fun parseLiveAssetPositions(data: String): List<Asset> {
        val gson: Gson = GsonBuilder()
            .setDateFormat("yyyy-MM-dd HH:mm:ss")
            .create()
        return gson.fromJson(data, object : TypeToken<List<Asset>>() {}.type)
    }

    fun parseAssetPositionHistory(data: String): List<AssetPositionHistory> {
        val gson: Gson = GsonBuilder()
            .setDateFormat("yyyy-MM-dd HH:mm:ss")
            .create()
        return gson.fromJson(data, object : TypeToken<List<AssetPositionHistory>>() {}.type)
    }

    fun parseAssetZoneHistory(data: String): List<AssetZoneHistory> {
        val gson: Gson = GsonBuilder()
            .setDateFormat("yyyy-MM-dd HH:mm:ss")
            .create()
        return gson.fromJson(data, object : TypeToken<List<AssetZoneHistory>>() {}.type)
    }*/
}