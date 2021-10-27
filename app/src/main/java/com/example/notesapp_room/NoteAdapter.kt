package com.example.notesapp_room

import android.graphics.Color
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.notesapp_room.databinding.ItemRowBinding


class NoteAdapter ( private val activity: MainActivity,private val items:List<Note>):RecyclerView.Adapter< NoteAdapter.ItemViewHolder>() {
    class ItemViewHolder(val binding: ItemRowBinding):RecyclerView.ViewHolder(binding.root) {

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        return ItemViewHolder(ItemRowBinding.inflate(LayoutInflater.from(parent.context),parent,false))
    }

    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        val item = items[position]
        holder.binding.apply {
            tvNote.text=item.noteText
            if(position%2==0){
                llNoteHolder.setBackgroundColor(Color.LTGRAY)
            }
            imEdit.setOnClickListener {
                activity.raiseDialog(item.id)
            }
            imDelete.setOnClickListener {
                activity.deleteNote(item.id)
            }
        }
    }

    override fun getItemCount()=items.size

}