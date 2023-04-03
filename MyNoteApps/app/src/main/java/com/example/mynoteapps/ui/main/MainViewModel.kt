package com.example.mynoteapps.ui.main

import android.app.Application
import androidx.lifecycle.ViewModel
import com.example.mynoteapps.repository.NoteRepository

class MainViewModel(application: Application) : ViewModel() {
    private val mNoteRepository: NoteRepository = NoteRepository(application)

    fun getAllNotes() = mNoteRepository.getAllNotes()
}