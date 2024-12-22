package hr.foi.air.indoorlocalization.parser

import hr.foi.air.core.models.impl.FloorMap
import hr.foi.air.core.models.impl.Zone
import com.google.gson.Gson
import hr.foi.air.core.parser.DataParser
import hr.foi.air.core.parser.floorMapList
import hr.foi.air.core.parser.zonesList
import kotlinx.serialization.json.*


class JsonDataParser(): DataParser {
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