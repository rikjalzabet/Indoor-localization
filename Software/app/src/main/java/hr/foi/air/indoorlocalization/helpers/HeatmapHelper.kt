package hr.foi.air.indoorlocalization.helpers

import android.icu.text.DateFormat
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import hr.foi.air.core.models.HeatmapDot
import hr.foi.air.core.models.impl.Asset
import hr.foi.air.core.models.impl.AssetPositionHistory
import hr.foi.air.core.parser.historyAssetPositionList
import hr.foi.air.core.parser.liveAssetPositionList
import java.util.Date
import kotlin.math.max


fun calculateColorForFrequency(frequency: Int): Color {
    return when {
        frequency < 10 -> Color.Blue.copy(alpha = 0.5f)
        frequency < 20 -> Color.Green.copy(alpha = 0.5f)
        frequency < 30 -> Color.Yellow.copy(alpha = 0.5f)
        else -> Color.Red.copy(alpha = 0.5f)
    }
}

fun calculateSizeForFrequency(frequency: Int): Float {
    return 35f - (frequency * 2).coerceIn(5, 30)
}

fun calculateSizeForColor(color: Color, dot: HeatmapDot): Float {
    return when (color) {
        Color.Blue -> dot.maxSize
        Color.Green -> dot.maxSize-10
        Color.Yellow -> dot.minSize
        else -> dot.maxSize
    }
}


fun calculateHeatmapDotsInDateRange(floorMapId : Int, size : Size, data : List<AssetPositionHistory>, from_date: Date, to_date: Date,
                                    maxFrequency : Int) : List<HeatmapDot> {

    val historyAssetPositions = historyAssetPositionList.filter { it.floorMapId == floorMapId }
    val historyWithinRange = historyAssetPositions.filter { it.dateTime in from_date..to_date}
    val groupedHistory = historyWithinRange.groupBy {
        Offset(
            x = (it.x / size.width),
            y = (it.y / size.height)
        )
    }

    val heatmapDots = groupedHistory.map{ (position, items) ->
        val frequency = items.size
        val alpha = (frequency.toFloat() / maxFrequency).coerceIn(0.2f, 1.0f)
        val dotSize = (frequency.toFloat() / maxFrequency).coerceIn(15f, 100f)

        HeatmapDot(
            position = position,
            frequency = frequency,
            alpha = alpha,
            size = dotSize
        )
    }

    return heatmapDots
}