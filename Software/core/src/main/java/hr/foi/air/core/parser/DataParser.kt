package hr.foi.air.core.parser

interface DataParser {
    fun updateZones(json: String)
    fun updateFloorMaps(json: String)
}