package com.example.githubapp.ui.viewmodel

import android.view.View
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class HomeViewModel : ViewModel() {

    private val _text = MutableLiveData<String>().apply {
        value = "This is home Fragment"
    }
    val text: LiveData<String> = _text

    private val _progressBar = MutableLiveData<Int>().apply {
        value = View.GONE
    }

    val progressBar: LiveData<Int> = _progressBar


}