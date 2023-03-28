package com.example.myviewmodel

import androidx.lifecycle.ViewModel

class MainViewModel: ViewModel() {

    var result = 0;

    fun calculate(width: String, length: String, height: String) {
        result = width.toInt() * length.toInt() * height.toInt()
    }
}