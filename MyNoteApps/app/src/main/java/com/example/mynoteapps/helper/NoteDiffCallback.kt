package com.example.mynoteapps.helper

import androidx.recyclerview.widget.DiffUtil
import com.example.mynoteapps.database.Note

class NoteDiffCallback(private val mOldNoteList: List<Note>, private val mNewNoteList: List<Note>) :
    DiffUtil.Callback() {
    override fun getOldListSize(): Int {
        return mOldNoteList.size
    }

    override fun getNewListSize(): Int {
        return mNewNoteList.size
    }

    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return mOldNoteList[oldItemPosition].id == mNewNoteList[newItemPosition].id
    }

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        val oldNote = mOldNoteList[oldItemPosition]
        val newNote = mNewNoteList[newItemPosition]
        return oldNote.title == newNote.title && oldNote.description == newNote.description && oldNote.date == newNote.date
    }
}