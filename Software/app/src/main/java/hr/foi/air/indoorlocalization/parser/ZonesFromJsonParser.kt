package hr.foi.air.indoorlocalization.parser

import android.util.Log
import androidx.compose.runtime.mutableStateListOf
import com.google.gson.Gson
import hr.foi.air.indoorlocalization.models.IZone
import hr.foi.air.indoorlocalization.models.Point
import hr.foi.air.indoorlocalization.models.impl.Zone
import kotlinx.serialization.json.*

fun parseZonesFromJson(json: String): List<IZone> {
    /*val parsedZones = Json.decodeFromString<List<Zone>>(json)
     return parsedZones.map { zoneJson ->
         Zone(
             id = zoneJson.id,
             name = zoneJson.name,
             points = zoneJson.points.map { pointJson ->
                 Point(
                     (pointJson.x).toFloat(),
                     (pointJson.y).toFloat(),
                     pointJson.ordinalNumber
                 )
             }
         )
     }*/
    return try {
        val parsedZones = Json.decodeFromString<List<Zone>>(json)
        parsedZones.map { zoneJson ->
            Zone(
                id = zoneJson.id,
                name = zoneJson.name,
                points = zoneJson.points.map { pointJson ->
                    Point(
                        (pointJson.x).toFloat(),
                        (pointJson.y).toFloat(),
                        pointJson.ordinalNumber
                    )
                }
            )
        }
    } catch (e: Exception) {
        Log.e("parseZonesFromJsonF", "Error parsing JSON", e)
        emptyList()
    }
}
val zonesList = mutableStateListOf<IZone>()

fun updateZonesFromJson(json: String) {
    /*val newZones = parseZonesFromJson(json)
    val existingIds = zonesList.map { it.id }.toSet()

    newZones.forEach { zone ->
        if (zone.id !in existingIds) {
            zonesList.add(zone)
        }
    }*/
    val gson = Gson()
    val newZones = gson.fromJson(json, Array<Zone>::class.java).toList()
    newZones.forEach { newZone ->
        if (zonesList.none { it.id == newZone.id }) {
            zonesList.add(newZone)
        }
    }
}