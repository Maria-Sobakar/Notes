package database

import android.content.Context
import androidx.room.Room

object DatabaseBuilder{
    private var instance: NoteDatabase? = null

    fun getInstance(context: Context): NoteDatabase{
        if (instance==null){
            synchronized(NoteDatabase::class.java){
                instance = buildRoomDB(context)
            }
        }
        return instance!!
    }

    private fun buildRoomDB(context: Context) =
        Room.databaseBuilder(
            context.applicationContext,
            NoteDatabase::class.java,
            "note-database"
        ).build()
}