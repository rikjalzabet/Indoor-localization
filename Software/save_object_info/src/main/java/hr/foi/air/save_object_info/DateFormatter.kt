package hr.foi.air.save_object_info

import java.util.Date

interface DateFormatter {
    fun format(date: Date?): String
}