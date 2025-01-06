package hr.foi.air.indoorlocalization.helpers

import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import hr.foi.air.core.models.HeatmapDot
import hr.foi.air.core.models.impl.AssetPositionHistory
import java.time.Instant
import java.util.Date

fun calculateColorForFrequency(frequency: Int, maxFrequency: Int): Color {
    return when {
        frequency > 0.8 * maxFrequency -> Color.Red
        frequency > 0.6 * maxFrequency -> Color(0xFFFF8400) // Orange
        frequency > 0.4 * maxFrequency -> Color.Yellow
        frequency > 0.2 * maxFrequency -> Color.Green
        else -> Color.Blue
    }
}
// unused, leaving for legacy

/*
fun calculateSizeForFrequency(frequency: Int, maxFrequency: Int): Float {
    // (minsize + (maxsize - minsize) * noramlizedfrequency).coerceIn(minsize, maxsize)
    return ((frequency.toFloat() / maxFrequency) * 20f).coerceIn(30f, 50f)
}

fun calculateSizeForColor(color: Color, dot: HeatmapDot): Float {
    return when (color) {
        Color.Blue -> dot.maxSize
        Color.Green -> dot.maxSize-10
        Color.Yellow -> dot.minSize
        else -> dot.maxSize
    }
}
*/

fun calculateHeatmapDotsInDateRange(floorMapId : Int, size : Size, fromDate: Date, toDate: Date,
                                    maxFrequency : Int) : List<HeatmapDot> {

    // currently using dummy data; to-refactor

    val historyAssetPositions = List(10000){
        generateRandomAssetPositionHistory(floorMapId, size)
    }

    val historyWithinRange = historyAssetPositions.filter { it.dateTime in fromDate..toDate}
    val areaSizeToGroupBy = 32f

    val groupedHistory = historyWithinRange.groupBy {
        Offset(
            x = ((it.x / areaSizeToGroupBy).toInt() * areaSizeToGroupBy),
            y = ((it.y / areaSizeToGroupBy).toInt() * areaSizeToGroupBy)
        )
    }
    val heatmapDots = groupedHistory.map{ (position, items) ->
        val rawFrequency = items.size
        val frequency = rawFrequency.coerceIn(0, maxFrequency)

        HeatmapDot(
            position =  Offset(position.x + areaSizeToGroupBy/2, position.y + areaSizeToGroupBy/2),
            frequency = frequency,
            size = areaSizeToGroupBy
        )
    }
    return heatmapDots
}


// FOR TESTING PURPOSES
// generates dummy data
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
// generates dummy data
fun generateRandomDate() : Date { // for live testing purposes
    val year = kotlin.random.Random.nextInt(2023, 2025)
    val month = kotlin.random.Random.nextInt(1, 12)
    val day = kotlin.random.Random.nextInt(1,28) // february safety
    val hour = kotlin.random.Random.nextInt(0, 23)
    val second = kotlin.random.Random.nextInt(0, 59)

    val monthString : String
    val dayString : String
    val hourString : String
    val secondString : String

    if (month < 10){
        monthString = "0$month"
    } else monthString = month.toString()
    if (day < 10){
        dayString = "0$day"
    } else dayString = day.toString()
    if (hour < 10){
        hourString = "0$hour"
    } else hourString = hour.toString()
    if (second < 10){
        secondString = "0$second"
    }
    else secondString = second.toString()

    val dateString = year.toString() + "-" +
            monthString + "-" +
            dayString + "T" +
            hourString + ":"+
            secondString + ":" +
            "00.00Z"

    return Date.from(Instant.parse(dateString))
}