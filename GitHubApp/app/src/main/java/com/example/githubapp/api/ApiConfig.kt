package com.example.githubapp.api

import android.content.Context
import com.chuckerteam.chucker.BuildConfig
import com.chuckerteam.chucker.api.ChuckerInterceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit

class ApiConfig {
    companion object {
        fun getApiService(context: Context): ApiService {
            val loggingInterceptor = if (BuildConfig.DEBUG) {
                HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY)
            } else {
                HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.NONE)
            }

            val githubClient = OkHttpClient.Builder()
                .addInterceptor(ChuckerInterceptor(context))
                .build()

            val retrofit = Retrofit.Builder()
                .baseUrl("https://api.github.com/")
                .client(githubClient)
                .build()

            return retrofit.create(ApiService::class.java)
        }
    }
}