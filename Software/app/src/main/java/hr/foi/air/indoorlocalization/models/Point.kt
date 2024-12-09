package hr.foi.air.indoorlocalization.models

import kotlinx.android.parcel.Parcelize
import kotlinx.serialization.Serializable

@Serializable
data class Point(
    val x: Float,
    val y: Float,
    val ordinalNumber: Int
)
