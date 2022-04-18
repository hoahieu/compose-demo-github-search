package com.hieulh.test.demoproj2.data.api

import com.hieulh.test.demoproj2.BuildConfig
import com.hieulh.test.demoproj2.data.pojo.SearchResultEntity
import com.hieulh.test.demoproj2.data.pojo.SearchUserEntity
import com.hieulh.test.demoproj2.data.pojo.UserDetailEntity
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Path
import retrofit2.http.Query

interface GithubApiService {
    @GET("search/users")
    @Headers("Authorization: token ${BuildConfig.GithubAccessToken}")
    suspend fun searchUser(@Query("q") query: String, @Query("page") page: Int): SearchResultEntity<SearchUserEntity>

    @GET("users/{username}")
    @Headers("Authorization: token ${BuildConfig.GithubAccessToken}")
    suspend fun getUserDetail(@Path("username") username: String): UserDetailEntity
}