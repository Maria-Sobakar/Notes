package com.marias.android.notes.data.dto


import androidx.room.Entity
import androidx.room.PrimaryKey
import java.text.DateFormat
import java.util.*

@Entity
data class Note(
    @PrimaryKey val id: UUID = UUID.randomUUID(),
    var title: String = "",
    var date: Date = Date(),
    var archived: Boolean = false,
    var text: String = "",
    var isPinned:Boolean = false
):Item()
    fun Date.toFormat():String = DateFormat.getDateInstance(DateFormat.MEDIUM).format(this)

