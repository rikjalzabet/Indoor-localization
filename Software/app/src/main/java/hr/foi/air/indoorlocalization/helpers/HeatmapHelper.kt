package hr.foi.air.indoorlocalization.helpers

import android.icu.text.DateFormat
import android.util.Log
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import hr.foi.air.core.models.HeatmapDot
import hr.foi.air.core.models.impl.Asset
import hr.foi.air.core.models.impl.AssetPositionHistory
import hr.foi.air.core.parser.historyAssetPositionList
import hr.foi.air.core.parser.liveAssetPositionList
import java.time.Instant
import java.util.Date
import java.util.Random
import kotlin.math.max


fun calculateColorForFrequency(frequency: Int, maxFrequency: Int): Color {
    return when {
        frequency > 0.8 * maxFrequency -> Color.Red
        frequency > 0.6 * maxFrequency -> Color(0xFFFF9861) // Orange
        frequency > 0.4 * maxFrequency -> Color.Yellow
        frequency > 0.2 * maxFrequency -> Color.Green
        else -> Color.Blue
    }
}

fun calculateSizeForFrequency(frequency: Int, maxFrequency: Int): Float {
    // (minsize + (maxsize - minsize) * noramlizedfrequency).coerceIn(minsize, maxsize)
    return ((frequency.toFloat() / maxFrequency) * 40f).coerceIn(10f, 50f)
}

fun calculateSizeForColor(color: Color, dot: HeatmapDot): Float {
    return when (color) {
        Color.Blue -> dot.maxSize
        Color.Green -> dot.maxSize-10
        Color.Yellow -> dot.minSize
        else -> dot.maxSize
    }
}


fun calculateHeatmapDotsInDateRange(floorMapId : Int, size : Size, from_date: Date, to_date: Date,
                                    maxFrequency : Int) : List<HeatmapDot> {

    // val historyAssetPositions = historyAssetPositionList.filter { it.floorMapId == floorMapId }
    val historyAssetPositions = List(5000){
        generateRandomAssetPositionHistory(floorMapId, size)
    }
    //Log.d("Heatmap", "historyAssetPositions size: ${historyAssetPositions.size}")
    val historyWithinRange = historyAssetPositions.filter { it.dateTime in from_date..to_date}
    //Log.d("Heatmap", "historyWithinRange size: ${historyWithinRange.size}")
    val areaSizeToGroupBy = 50f
    val groupedHistory = historyWithinRange.groupBy {
        Offset(
            x = ((it.x / size.width) * size.width / areaSizeToGroupBy).toInt() * areaSizeToGroupBy,
            y = ((it.y / size.height) * size.height / areaSizeToGroupBy).toInt() * areaSizeToGroupBy
        )
    }
    //Log.d("Heatmap", "groupedHistory size: ${historyWithinRange.size}")


    val heatmapDots = groupedHistory.map{ (position, items) ->
        val rawFrequency = items.size
        val frequency = rawFrequency.coerceIn(0, maxFrequency)
        //val alpha = (frequency.toFloat() / maxFrequency).coerceIn(0.2f, 1.0f)
        val alpha = 0.5f
        val dotSize = calculateSizeForFrequency(frequency, maxFrequency)

        HeatmapDot(
            position =  Offset(position.x + areaSizeToGroupBy/2, position.y + areaSizeToGroupBy/2),
            frequency = frequency,
            alpha = alpha,
            size = dotSize
        )
    }
    //Log.d("Heatmap", "heatmapDots size: ${heatmapDots.size}")
    return heatmapDots
}


// FOR TESTING PURPOSES

fun generateRandomAssetPositionHistory(floormapId : Int, maxSize : Size) : AssetPositionHistory {
    return AssetPositionHistory(
        id = kotlin.random.Random.nextInt(0, 100),
        assetId = 1,
        dateTime = generateRandomDate(),
        x = kotlin.random.Random.nextInt(0, maxSize.width.toInt()).toFloat(),
        y = kotlin.random.Random.nextInt(0, maxSize.height.toInt()).toFloat(),
        floorMapId = floormapId
    )
}

// FOR TESTING PURPOSES
fun generateRandomDate() : Date { // for live testing purposes
    val year = kotlin.random.Random.nextInt(2023, 2025)
    val month = kotlin.random.Random.nextInt(1, 12)
    val day = kotlin.random.Random.nextInt(1,28) // february safety
    val hour = kotlin.random.Random.nextInt(0, 23)
    val second = kotlin.random.Random.nextInt(0, 59)

    val month_string : String
    val day_string : String
    val hour_string : String
    val second_string : String

    if (month < 10){
        month_string = "0$month"
    } else month_string = month.toString()
    if (day < 10){
        day_string = "0$day"
    } else day_string = day.toString()
    if (hour < 10){
        hour_string = "0$hour"
    } else hour_string = hour.toString()


    if (second < 10){
        second_string = "0$second"
    }
    else second_string = second.toString()



    val dateString = year.toString() + "-" +
            month_string + "-" +
            day_string + "T" +
            hour_string + ":"+
            second_string + ":" +
            "00.00Z"

    return Date.from(Instant.parse(dateString))

}