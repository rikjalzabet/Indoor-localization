package hr.foi.air.core.models

import java.util.Date

interface IAssetPositionHistory {
    val id: Int
    val assetId: Int
    val x: Float
    val y: Float
    val dateTime: String
    val floorMapId: Int
}