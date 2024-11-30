package hr.foi.air.indoorlocalization.models

interface IZone {
    val id: Int
    val name: String
    val points: List<Point>
}