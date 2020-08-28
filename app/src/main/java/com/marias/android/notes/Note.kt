package com.marias.android.notes



import java.util.*

data class Note (
    val id: UUID = UUID.randomUUID(),
    var title:String = "",
    var date:Date = Date(),
    var text:String = ""

)