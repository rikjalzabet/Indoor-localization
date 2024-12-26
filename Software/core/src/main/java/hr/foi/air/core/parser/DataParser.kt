package hr.foi.air.core.parser

interface DataParser {
    fun updateZones(data: String)
    fun updateFloorMaps(data: String)
    fun updateLiveAssetPositions (data: String)
}