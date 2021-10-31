package com.example.notesapp_room

import android.content.DialogInterface
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.async
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {
/**
   * This project makes Room work without a View Model, the Recycler View is only updated
   * each time the screen is rotated.
  */


    private val noteDao by lazy { NoteDatabase.getDatabase(this).noteDao() }
    private val repository by lazy { NoteRepository(noteDao) }

    private lateinit var edNote:EditText
    private lateinit var btnSubmit: Button
    private lateinit var rvMain:RecyclerView

    private lateinit var notes:List<Note>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        notes= listOf()

        edNote=findViewById(R.id.edNotes)
        btnSubmit=findViewById(R.id.btnSubmit)

        btnSubmit.setOnClickListener {

            val note = edNote.text.toString()
            addNote(note)
            edNote.text.clear()
            edNote.clearFocus()
            updateRV()
        }
        getItemsList()
        rvMain=findViewById(R.id.rvMain)
        updateRV()
    }
    private fun updateRV() {
        rvMain.adapter=NoteAdapter(this,notes)
        rvMain.layoutManager=LinearLayoutManager(this)
    }

    private fun getItemsList() {
       CoroutineScope(IO).launch {
           val data =async{
               repository.getNotes
           }.await()
           if (data.isNotEmpty()){
               notes=data

               updateRV()
           }else{
               Log.e("MainActivity","Unable to get data")
           }
       }
    }

    private fun addNote(note: String) {
        CoroutineScope(IO).launch {
            repository.addNote(Note(0,note))
        }
    }
   private fun editNote(noteId:Int,noteText:String){
        CoroutineScope(IO).launch {
            repository.updateNote(Note(noteId,noteText))
        }
    }
    fun deleteNote(noteId:Int){
        CoroutineScope(IO).launch {
            repository.deleteNote(Note(noteId, ""))
        }
    }
    fun raiseDialog(id: Int) {
        val d = AlertDialog.Builder(this)
        val update = EditText(this)
        update.hint="Enter new text"
        d.setCancelable(false)
            .setPositiveButton("Save",DialogInterface.OnClickListener{
                _,_->run{editNote(id,update.text.toString())
                updateRV()
                }
            })
            .setNegativeButton("Cancel",DialogInterface.OnClickListener{
                dialog,_->dialog.cancel()
            })
        val alert = d.create()
        alert.setTitle("Update Note")
        alert.setView(update)
        alert.show()
    }
}
