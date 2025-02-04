package hr.foi.air.core.models

interface IAssetPositionHistory {
    val id: Int
    val assetId: Int
    val x: Float
    val y: Float
    val dateTime: String
    val floorMapId: Int
}