package hr.foi.air.indoorlocalization.parser

import android.util.Log
import androidx.compose.runtime.mutableStateListOf
import com.google.gson.Gson
import hr.foi.air.indoorlocalization.models.IFloorMap
import hr.foi.air.indoorlocalization.models.IZone
import hr.foi.air.indoorlocalization.models.Point
import hr.foi.air.indoorlocalization.models.impl.FloorMap
import hr.foi.air.indoorlocalization.models.impl.Zone
import kotlinx.serialization.json.*


val floorMapList = mutableStateListOf<IFloorMap>()
val zonesList = mutableStateListOf<IZone>()

/*fun updateZonesFromJson(json: String) {
    val gson = Gson()
    val newZones = gson.fromJson(json, Array<Zone>::class.java).toList()
    newZones.forEach { newZone ->
        if (zonesList.none { it.id == newZone.id }) {
            zonesList.add(newZone)
        }
    }
}

fun updateFloorMapsFromJson(json: String) {
    val gson = Gson()
    val newFloorMaps = gson.fromJson(json, Array<FloorMap>::class.java).toList()

    newFloorMaps.forEach { newFloorMap ->
        if (floorMapList.none { it.id == newFloorMap.id }) {
            floorMapList.add(newFloorMap)
        }
    }
}*/
class JsonDataParser():DataParser{
    override fun updateZones(json: String) {
        val gson = Gson()
        val newZones = gson.fromJson(json, Array<Zone>::class.java).toList()
        newZones.forEach { newZone ->
            if (zonesList.none { it.id == newZone.id }) {
                zonesList.add(newZone)
            }
        }
    }

    override fun updateFloorMaps(json: String) {
        val gson = Gson()
        val newFloorMaps = gson.fromJson(json, Array<FloorMap>::class.java).toList()

        newFloorMaps.forEach { newFloorMap ->
            if (floorMapList.none { it.id == newFloorMap.id }) {
                floorMapList.add(newFloorMap)
            }
        }
    }
}