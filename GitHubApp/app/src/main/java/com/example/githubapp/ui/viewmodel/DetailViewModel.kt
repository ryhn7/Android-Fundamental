package com.example.githubapp.ui.viewmodel

import android.app.Application
import android.util.Log
import androidx.lifecycle.*
import com.example.githubapp.data.remote.retrofit.ApiConfig
import com.example.githubapp.data.remote.response.DataUser
import retrofit2.Callback
import retrofit2.Response
import com.example.githubapp.data.Result
import com.example.githubapp.data.UserRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DetailViewModel @Inject constructor(private val repo: UserRepository) : ViewModel() {


    private val _user = MutableStateFlow<Result<DataUser>>(Result.Loading)
    val user = _user.asStateFlow()

    private val _isLoading = MutableStateFlow(false)
    val isLoading = _isLoading.asStateFlow()


    fun getUserDetail(username: String) {
        _user.value = Result.Loading
        viewModelScope.launch {
            repo.getUserDetail(username).collect {
                _user.value = it
            }
        }
        _isLoading.value = true
    }

}