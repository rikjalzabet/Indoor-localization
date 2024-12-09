package hr.foi.air.indoorlocalization.parser

import hr.foi.air.indoorlocalization.models.IZone
import hr.foi.air.indoorlocalization.models.Point
import hr.foi.air.indoorlocalization.models.impl.Zone
import kotlinx.serialization.json.*

fun parseZonesFromJson(json: String): List<IZone> {
    val parsedZones = Json.decodeFromString<List<Zone>>(json)
    return parsedZones.map { zoneJson ->
        Zone(
            id = zoneJson.id,
            name = zoneJson.name,
            points = zoneJson.points.map { pointJson ->
                Point(pointJson.x, pointJson.y, pointJson.ordinalNumber)
            }
        )
    }
}