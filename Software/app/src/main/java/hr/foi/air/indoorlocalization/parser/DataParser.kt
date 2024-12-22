package hr.foi.air.indoorlocalization.parser

import androidx.compose.runtime.mutableStateListOf

interface DataParser {
    fun updateZones(json: String)
    fun updateFloorMaps(json: String)
}