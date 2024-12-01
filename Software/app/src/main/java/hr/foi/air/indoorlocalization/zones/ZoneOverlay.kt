package hr.foi.air.indoorlocalization.zones

import androidx.compose.runtime.Composable
import hr.foi.air.indoorlocalization.models.IZone
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.*
import androidx.compose.ui.graphics.drawscope.Fill
import androidx.compose.ui.graphics.Color

@Composable
fun ZoneOverlay(zone: IZone){
    Canvas(modifier = Modifier.fillMaxSize()) {
        val path = Path()
        val points = zone.points.map { point ->
            val scaledX = point.x * size.width
            val scaledY = point.y * size.height
            scaledX to scaledY
        }

        if (points.isNotEmpty()) {
            path.moveTo(points.first().first, points.first().second)
            points.forEach { (x, y) ->
                path.lineTo(x, y)
            }
            path.close()

            drawPath(
                path = path,
                color = Color(0xFFADD8E6),
                style = Fill
            )
        }

        points.zipWithNext { start, end ->
            drawLine(
                color = Color.Blue,
                start = androidx.compose.ui.geometry.Offset(start.first, start.second),
                end = androidx.compose.ui.geometry.Offset(end.first, end.second),
                strokeWidth = 4f
            )
        }

        points.forEach { (x, y) ->
            drawCircle(
                color = Color.Blue,
                radius = 8f,
                center = androidx.compose.ui.geometry.Offset(x, y)
            )
        }
    }
}