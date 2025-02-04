package hr.foi.air.core.models

interface IAsset {
    val id: Int
    val name: String
    val x: Float
    val y: Float
    val lastSync: String
    val floorMapId: Int
    val active: Boolean
}