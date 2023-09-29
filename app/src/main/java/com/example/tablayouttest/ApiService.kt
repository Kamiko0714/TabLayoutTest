package com.example.tablayouttest

import retrofit2.Call
import retrofit2.http.*
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiService {
    @GET("search/users")
    fun searchUsers(@Query("q") username: String): Call<SearchResponse>

    @GET("users/{username}")
    fun getUserDetails(@Path("username") username: String): Call<User>

    @GET("users/{username}/followers")
    fun getUserFollowers(@Path("username") username: String): Call<List<User>>

    @GET("users/{username}/following")
    fun getUserFollowing(@Path("username") username: String): Call<List<User>>
}