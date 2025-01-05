package hr.foi.air.indoorlocalization.helpers

import android.icu.text.DateFormat
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import hr.foi.air.core.models.HeatmapDot
import hr.foi.air.core.models.impl.Asset
import hr.foi.air.core.models.impl.AssetPositionHistory
import hr.foi.air.core.parser.historyAssetPositionList
import hr.foi.air.core.parser.liveAssetPositionList
import java.util.Date


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
        else -> dot.liveMovementSize
    }
}

fun calculateDotFrequency() {
    // TODO
}

fun groupAssetPositionHistoryByTime(data: List<AssetPositionHistory>): Map<Date, List<AssetPositionHistory>>{
    return data.groupBy { it.dateTime }
}

fun convertAssetPositionHistoryToHeatmapPoints(floorMapId : Int, data: List<AssetPositionHistory>) : List<HeatmapDot>{

    val historyAssetPositions = historyAssetPositionList.filter { it.floorMapId == floorMapId }

   // TODO

}