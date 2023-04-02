package com.example.githubapp.ui.viewmodel

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.githubapp.api.ApiConfig
import com.example.githubapp.model.DataUser
import retrofit2.Callback
import retrofit2.Response

class DetailViewModel(application: Application) : AndroidViewModel(application) {

    private val context = getApplication<Application>().applicationContext

    private val _user = MutableLiveData<DataUser?>(null)
    val user: LiveData<DataUser?> = _user

    private val _isLoading = MutableLiveData(true)
    val isLoading: LiveData<Boolean> = _isLoading

    private val _isError = MutableLiveData(false)
    val isError: LiveData<Boolean> = _isError

    private val _callCounter = MutableLiveData(0)
    val callCounter: LiveData<Int> = _callCounter

    companion object {
        private const val TAG = "DetailViewModel"
    }

    fun getUserDetail(username: String) {
        _isLoading.value = true
        _callCounter.value = 1

        val githubClient = ApiConfig.getApiService(context)
            .getUserDetailByUsername(username)
        githubClient.enqueue(object : Callback<DataUser> {
            override fun onResponse(call: retrofit2.Call<DataUser>, response: Response<DataUser>) {
                if (response.isSuccessful) {
                    _user.value = response.body()
                } else {
                    Log.e(TAG, response.message())
                }

                _isLoading.value = false
                _isError.value = false
            }

            override fun onFailure(call: retrofit2.Call<DataUser>, t: Throwable) {
                Log.e(TAG, t.message.toString())

                _isLoading.value = false
                _isError.value = true
            }
        })
    }

}