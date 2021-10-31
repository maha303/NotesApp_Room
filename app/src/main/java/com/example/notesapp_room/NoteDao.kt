package com.example.notesapp_room

import androidx.room.*

@Dao
interface NoteDao {
    @Query("SELECT * FROM NotesTable ORDER BY id ASC")
    fun getNote():List<Note>

    @Insert(onConflict = OnConflictStrategy.IGNORE)

  suspend  fun addNote(note: Note)

    @Update
   suspend fun updateNote(note: Note)

    @Delete
  suspend  fun deleteNote(note: Note)
}
