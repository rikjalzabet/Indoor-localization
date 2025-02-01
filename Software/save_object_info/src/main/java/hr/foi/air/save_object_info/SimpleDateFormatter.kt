package hr.foi.air.save_object_info

import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class SimpleDateFormatter : DateFormatter {
    private val dateFormat = SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault())

    override fun format(date: Date?): String {
        return date?.let { dateFormat.format(it) } ?: "N/A"
    }
}