package hr.foi.air.indoorlocalization.helpers

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import hr.foi.air.core.models.HeatmapDot
import hr.foi.air.core.models.impl.AssetPositionHistory
import java.time.Instant
import java.util.Date
import hr.foi.air.core.models.HeatmapLiveDot
import hr.foi.air.core.parser.assetPositionHistoryList
import java.text.SimpleDateFormat
import java.time.LocalDateTime
import java.time.ZoneId
import java.time.format.DateTimeFormatter
import java.util.Locale

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
fun calculateColorForFrequencyLiveAsset(frequency: Int): Color {
    return when {
        frequency < 10 -> Color.Blue.copy(alpha = 0.5f)
        frequency < 20 -> Color.Green.copy(alpha = 0.5f)
        frequency < 30 -> Color.Yellow.copy(alpha = 0.5f)
        else -> Color.Red.copy(alpha = 0.5f)
    }
}

/*fun calculateSizeForFrequencyLiveAsset(frequency: Int): Float {
    return 35f - (frequency * 2).coerceIn(5, 30)
}*/

fun calculateSizeForColorLiveAsset(color: Color, dot: HeatmapLiveDot): Float {
    return when (color) {
        Color.Blue -> dot.maxSize
        Color.Green -> dot.maxSize-10
        Color.Yellow -> dot.minSize
        else -> dot.liveMovementSize
    }
}

@Composable
fun calculateHeatmapDotsInDateRange(floorMapId : Int, size : Size, fromDate: Date, toDate: Date,
                                    maxFrequency : Int) : List<HeatmapDot> {

    // currently using dummy data; to-refactor

    //val historyAssetPositions = List(10000){
     //   generateRandomAssetPositionHistory(floorMapId, size)
    //}

    val historyAssetPositions = remember { assetPositionHistoryList }


    val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSS")

    val historyWithinRange = historyAssetPositions.filter {
        val dateTimeParts = it.dateTime.split(".") // Split at decimal point
        val truncatedDateTime = if (dateTimeParts.size == 2) {
            val fraction = dateTimeParts[1].take(3) // Take only the first 3 digits
            "${dateTimeParts[0]}.$fraction" // Reconstruct the timestamp
        } else {
            it.dateTime // No fractional part, use as is
        }

        val date = LocalDateTime.parse(truncatedDateTime, formatter)
            .atZone(ZoneId.systemDefault())
            .toInstant()
            .let { Date.from(it) }

        date in fromDate..toDate
    }



    val areaSizeToGroupBy = 1

    val groupedHistory = historyWithinRange.groupBy {
        Offset(
            x = ((it.x / areaSizeToGroupBy).toInt() * areaSizeToGroupBy) - areaSizeToGroupBy/2f,
            y = ((it.y / areaSizeToGroupBy).toInt() * areaSizeToGroupBy) - areaSizeToGroupBy/2f
        )
    }
    val heatmapDots = groupedHistory.map{ (position, items) ->
        val rawFrequency = items.size
        val frequency = rawFrequency.coerceIn(0, maxFrequency)

        HeatmapDot(
            position =  Offset(position.x, position.y),
            frequency = frequency,
            size = areaSizeToGroupBy.toFloat()
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
        dateTime = generateRandomDate().toString(),
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