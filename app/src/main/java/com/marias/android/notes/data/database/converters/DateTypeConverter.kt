package com.marias.android.notes.data.database.converters

import androidx.room.TypeConverter
import java.util.*

class DateTypeConverter{

    @TypeConverter
    fun fromDate(date: Date?): Long? {
        return date?.time
    }
    @TypeConverter
    fun toDate(millisSinceEpoch: Long?): Date? {
        return millisSinceEpoch?.let {
            Date(it)
        }
    }
}