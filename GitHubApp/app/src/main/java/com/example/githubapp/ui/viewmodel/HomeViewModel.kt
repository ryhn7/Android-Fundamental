package com.example.githubapp.ui.viewmodel

import android.app.Application
import android.util.Log
import android.view.View
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.githubapp.Utils
import com.example.githubapp.api.ApiConfig
import com.example.githubapp.model.GithubUserResponse
import com.example.githubapp.model.User
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class HomeViewModel(application: Application) : AndroidViewModel(application) {

    private val context = getApplication<Application>().applicationContext

    private val _user = MutableLiveData<ArrayList<User>>()
    val user: LiveData<ArrayList<User>> = _user

    private val _isLoading = MutableLiveData(true)
    val isLoading: LiveData<Boolean> = _isLoading

    private val _isError = MutableLiveData(false)
    val isError: LiveData<Boolean> = _isError

    companion object{
        private const val TAG = "MainViewModel"
    }

    init {
        findUser("\"\"")
    }

    fun findUser(query: String){
        _isLoading.value = true

        val githubClient = ApiConfig.getApiService(context).searchUsername(token = "Bearer ${Utils.TOKEN}" , query)
        githubClient.enqueue(object : Callback<GithubUserResponse> {
            override fun onResponse(
                call: Call<GithubUserResponse>,
                response: Response<GithubUserResponse>
            ) {
                _isLoading.value = false
                _isError.value = false
                if (response.isSuccessful){
                    val responseBody = response.body()
                    if(responseBody != null)  {
                        _user.value = response.body()?.items
                    }
                    else{
                        Log.e(TAG, "onFailure: ${response.message()}")
                    }
                }
            }

            override fun onFailure(call: Call<GithubUserResponse>, t: Throwable) {
                _isLoading.value = false
                _isError.value = true
                Log.e(TAG, "onFailure: ${t.message}")
            }
        })


    }

}