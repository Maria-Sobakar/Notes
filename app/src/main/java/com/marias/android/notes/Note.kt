package com.marias.android.notes



import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.*
@Entity
data class Note (
    @PrimaryKey val id: UUID = UUID.randomUUID(),
    var title:String = "",
    var date:Date = Date(),
    var text:String = ""

)