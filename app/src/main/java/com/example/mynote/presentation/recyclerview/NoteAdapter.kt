package com.example.mynote.presentation.recyclerview

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.mynote.data.entity.Note
import com.example.mynote.databinding.NoteItemBinding

class NoteAdapter:RecyclerView.Adapter<NoteAdapter.NoteHolder>() {

    var onClickListener : ((Note) -> Unit)? = null
    var onLongClickListener : ((Note, View) -> Unit)? = null
    var noteList = listOf<Note>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NoteHolder {
        val binding =  NoteItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return NoteHolder(binding)
    }

    override fun onBindViewHolder(holder: NoteHolder, position: Int) {
        val note = noteList[position]
        holder.binding.apply {
            titleText.text = noteList[position].title
            card.setCardBackgroundColor(note.backgroundColor)
        }
        holder.itemView.setOnClickListener {
            onClickListener?.invoke(note)
        }
        holder.itemView.setOnLongClickListener {
            onLongClickListener?.invoke(note, holder.itemView)
            true
        }
    }


    override fun getItemCount(): Int {
        return noteList.size
    }

    class NoteHolder(val binding: NoteItemBinding): RecyclerView.ViewHolder(binding.root){

    }

}