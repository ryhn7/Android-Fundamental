package com.example.githubapp.ui.viewmodel

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.githubapp.Utils
import com.example.githubapp.api.ApiConfig
import com.example.githubapp.model.User
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class FollowViewModel(application: Application) : AndroidViewModel(application) {

    private val context = getApplication<Application>().applicationContext

    private val _followers = MutableLiveData<ArrayList<User>?>(null)
    val followers: LiveData<ArrayList<User>?> = _followers

    private val _following = MutableLiveData<ArrayList<User>?>(null)
    val following: LiveData<ArrayList<User>?> = _following

    private val _isLoading = MutableLiveData(true)
    val isLoading: LiveData<Boolean> = _isLoading

    companion object {
        private const val TAG = "FollowViewModel"
    }

    fun getUserFollowers(username: String) {
        _isLoading.value = true

        val githubClient = ApiConfig.getApiService(context)
            .getUserFollowers(token = "Bearer ${Utils.TOKEN}", username)
        githubClient.enqueue(object : Callback<ArrayList<User>> {
            override fun onResponse(
                call: Call<ArrayList<User>>,
                response: Response<ArrayList<User>>
            ) {
                _isLoading.value = false
                if (response.isSuccessful) {
                    val responseBody = response.body()
                    if (responseBody != null) {
                        _followers.value = response.body()
                    } else {
                        Log.e(TAG, "onFailure: ${response.message()}")
                    }
                }
            }

            override fun onFailure(call: Call<ArrayList<User>>, t: Throwable) {
                _isLoading.value = false
                Log.e(TAG, "onFailure: ${t.message}")
            }
        })

    }

    fun getUserFollowing(username: String) {
        _isLoading.value = true

        val githubClient = ApiConfig.getApiService(context)
            .getUserFollowing(token = "Bearer ${Utils.TOKEN}", username)
        githubClient.enqueue(object : Callback<ArrayList<User>> {
            override fun onResponse(
                call: retrofit2.Call<ArrayList<User>>,
                response: Response<ArrayList<User>>
            ) {
                _isLoading.value = false
                if (response.isSuccessful) {
                    val responseBody = response.body()
                    if (responseBody != null) {
                        _following.value = response.body()
                    } else {
                        Log.e(TAG, "onFailure: ${response.message()}")
                    }
                }
            }

            override fun onFailure(call: retrofit2.Call<ArrayList<User>>, t: Throwable) {
                _isLoading.value = false
                Log.e(TAG, "onFailure: ${t.message}")
            }
        })
    }
}