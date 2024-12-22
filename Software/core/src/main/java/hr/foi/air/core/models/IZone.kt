package hr.foi.air.core.models

interface IZone {
    val id: Int
    val name: String
    val points: List<Point>
}