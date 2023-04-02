package com.example.githubapp.api

import com.example.githubapp.model.*
import retrofit2.Call
import retrofit2.http.*

interface ApiService {

    @GET("search/users")
    fun searchUsername(
        @Query("q") q: String
    ): Call<GithubUserResponse>

    @GET("users/{username}")
    fun getUserDetailByUsername(
        @Path("username") username: String
    ): Call<DataUser>

    @GET("users/{username}/followers")
    fun getUserFollowers(
        @Path("username") username: String
    ): Call<ArrayList<User>>

    @GET("users/{username}/following")
    fun getUserFollowing(
        @Path("username") username: String
    ): Call<ArrayList<User>>
}