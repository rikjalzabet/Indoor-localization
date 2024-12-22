package hr.foi.air.indoorlocalization.zones

import androidx.compose.runtime.Composable
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.*
import androidx.compose.ui.graphics.drawscope.Fill
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.drawscope.clipRect
import com.example.core.models.IZone

@Composable
fun ZoneOverlay(
    zone: IZone,
    imageSize: Size,
    imageOffset: Offset
){
    Canvas(
        modifier = Modifier
            .fillMaxSize()
    ) {
        clipRect(
            left=imageOffset.x,
            top=imageOffset.y,
            right=imageOffset.x + imageSize.width,
            bottom=imageOffset.y + imageSize.height
        ){
            val path = Path()
            val points = zone.points.map { point ->
                val scaledX = imageOffset.x + point.x * imageSize.width
                val scaledY = imageOffset.y + point.y * imageSize.height
                Offset(scaledX.toFloat(), scaledY.toFloat())
            }
            if (points.isNotEmpty()) {
                path.moveTo(points.first().x, points.first().y)
                points.forEach { offset ->
                    path.lineTo(offset.x, offset.y)
                }
                path.close()

                drawPath(
                    path = path,
                    color = Color(0xFFADD8E6).copy(alpha = 0.5f),
                    style = Fill
                )
            }
            points.zipWithNext { start, end ->
                drawLine(
                    color = Color.Blue,
                    start = start,
                    end = end,
                    strokeWidth = 4f
                )
            }

            points.forEach { point ->
                drawCircle(
                    color = Color.Blue,
                    radius = 8f,
                    center = point
                )
            }
        }
    }
}