package com.hieulh.test.demoproj2.data.pojo

import com.squareup.moshi.Json

data class SearchUserEntity(
    @Json(name= "login") val username: String,
    @Json(name= "id") val id: Int,
    @Json(name= "avatar_url") val avatarUrl: String? = null,
)