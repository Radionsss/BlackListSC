package com.stalcraft.blackliststalcraft.data.local.typeConverters

import androidx.room.TypeConverter
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.*

class DateConverter {
    private val dateFormat = SimpleDateFormat("yy.MM.dd", Locale.getDefault())

    @TypeConverter
    fun fromTimestamp(value: String?): Date? {
        return try {
            value?.let { dateFormat.parse(it) }
        } catch (e: ParseException) {
            e.printStackTrace()
            null
        }
    }

    @TypeConverter
    fun dateToTimestamp(date: Date?): String? {
        return date?.let { dateFormat.format(it) }
    }
}