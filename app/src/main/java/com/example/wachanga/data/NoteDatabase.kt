package com.example.wachanga.data

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.wachanga.data.datasource.NoteDao
import com.example.wachanga.domain.model.Note

@Database(
    entities = [Note::class],
    version = 1,
)
abstract class NoteDatabase : RoomDatabase() {

    abstract val noteDao: NoteDao

    companion object {
        const val DATABASE_NAME = "notes_db"
    }
}
