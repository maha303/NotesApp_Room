package com.example.notesapp_room

import androidx.room.Update

class NoteRepository (private val noteDao: NoteDao) {

    val getNotes:List<Note> = noteDao.getNote()

     suspend fun addNote(note: Note){
        noteDao.addNote(note)
    }
   suspend fun updateNote(note: Note){
        noteDao.updateNote(note)
    }
     suspend fun deleteNote(note: Note){
        noteDao.deleteNote(note)
    }
}