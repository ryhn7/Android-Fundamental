package com.example.newsapp.data.di

import android.content.Context
import com.example.newsapp.data.local.room.NewsDatabase
import com.example.newsapp.data.remote.retrofit.ApiConfig
import com.example.newsapp.utils.AppExecutors
import com.example.newsapp.data.NewsRepository

object Injection {

    fun provideRepository(context: Context) : NewsRepository {
        val apiService = ApiConfig.getApiService()
        val database = NewsDatabase.getInstance(context)
        val dao = database.newsDao()
        val appExecutors = AppExecutors()
        return NewsRepository.getInstance(apiService, dao, appExecutors)
    }
}