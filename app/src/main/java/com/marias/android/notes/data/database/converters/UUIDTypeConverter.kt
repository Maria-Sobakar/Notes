package com.marias.android.notes.data.database.converters

import androidx.room.TypeConverter
import java.util.*

class UUIDTypeConverter{

    @TypeConverter
    fun toUUID(uuid: String?): UUID? {
        return UUID.fromString(uuid)
    }
    @TypeConverter
    fun fromUUID(uuid: UUID?): String? {
        return uuid?.toString()
    }
}