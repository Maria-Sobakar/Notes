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
    var text: String = "",
    var archived: Boolean = false
) {
    fun dateFormat() = DateFormat.getDateInstance(DateFormat.MEDIUM).format(date)
}