package com.example.notesapp_room

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import java.time.Instant

@Database(entities = [Note::class],version = 1,exportSchema = false)
abstract class NoteDatabase :RoomDatabase (){
    abstract fun noteDao():NoteDao
    companion object{
        private var INSTANCE:NoteDatabase?=null
        fun getDatabase(context: Context):NoteDatabase{
            val temp = INSTANCE
            if(temp!=null){
                return temp
            }
            synchronized(this){
                val instant= Room.databaseBuilder(context.applicationContext,NoteDatabase::class.java,"note_db").fallbackToDestructiveMigration().build()
                INSTANCE=instant
                return instant
            }
        }

    }
}