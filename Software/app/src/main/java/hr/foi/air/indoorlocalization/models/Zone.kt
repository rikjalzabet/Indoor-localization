package hr.foi.air.indoorlocalization.models

interface Zone {
    val id: Int
    val name: String
    val points: List<Point>
}