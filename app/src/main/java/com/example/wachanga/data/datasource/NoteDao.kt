package com.example.wachanga.data.datasource

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.wachanga.domain.model.Note
import io.reactivex.rxjava3.core.Flowable
import io.reactivex.rxjava3.core.Observable

@Dao
interface NoteDao {

    @Query("SELECT * from note")
    fun getNotes(): Flowable<List<Note>>

    @Query("SELECT * from note WHERE id = :id")
    fun getNoteById(id: Long): Observable<Note>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertNote(note: Note)

    @Delete
    fun deleteNote(note: Note)
}
