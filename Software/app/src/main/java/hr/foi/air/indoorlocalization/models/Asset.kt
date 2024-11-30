package hr.foi.air.indoorlocalization.models

import java.util.Date

interface Asset {
    val id: Int
    val name: String
    val x: Float
    val y: Float
    val lastSync: Date
    val floorMapId: Int
    val active: Boolean
}