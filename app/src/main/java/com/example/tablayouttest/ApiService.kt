package com.example.tablayouttest

import retrofit2.Call
import retrofit2.http.*

interface ApiService {

    @GET("users/{username}")
    fun findUserDetailByUsername(@Path("username") username : String): Call<IndividualResponse>;

    @GET("search/users")
    fun searchUserDetailByLoginName(@Query("q") loginName : String): Call<SearchResponse>;

    @GET("users")
    fun listAllUsers(@Query("page") page : String): Call<List<IndividualResponse>>;

    @GET("users/{loginName}/followers")
    fun getFollowers(@Path("loginName") loginName: String): Call<List<IndividualResponse>>;

    @GET("users/{loginName}/following")
    fun getFollowing(@Path("loginName") loginName: String): Call<List<IndividualResponse>>;
}