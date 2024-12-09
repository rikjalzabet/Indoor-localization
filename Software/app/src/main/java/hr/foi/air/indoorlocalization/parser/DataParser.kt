package hr.foi.air.indoorlocalization.parser

import androidx.compose.runtime.mutableStateListOf
import hr.foi.air.indoorlocalization.models.IFloorMap
import hr.foi.air.indoorlocalization.models.IZone

interface DataParser {
    fun updateZones(json: String)
    fun updateFloorMaps(json: String)
}