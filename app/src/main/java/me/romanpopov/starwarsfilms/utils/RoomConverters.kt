package me.romanpopov.starwarsfilms.utils

import androidx.room.TypeConverter
import java.util.*

class RoomConverters {
    @TypeConverter
    fun dateToTimestamp(date: Date?): Long? {
        return date?.time
    }

    @TypeConverter
    fun timestampToDate(timestamp: Long?): Date? {
        return timestamp?.let { Date(it) }
    }

    @TypeConverter
    fun listToString(list: List<*>): String {
        return list.joinToString { it.toString() }
    }

    @TypeConverter
    fun stringToList(string: String): List<*> {
        return string.split(", ")
    }
}