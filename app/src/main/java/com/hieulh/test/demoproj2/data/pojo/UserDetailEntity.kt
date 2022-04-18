package com.hieulh.test.demoproj2.data.pojo

import com.squareup.moshi.Json

data class UserDetailEntity(
    @Json(name= "login") val username: String,
    @Json(name= "id") val id: Int,
    @Json(name= "avatar_url") val avatarUrl: String? = null,
    @Json(name= "gravatar_id") val gravatarId: String? = null,
    @Json(name= "url") val url: String? = null,
    @Json(name= "html_url") val htmlUrl: String? = null,
    @Json(name= "name") val name: String? = null,
    @Json(name= "company") val company: String? = null,
    @Json(name= "location") val location: String? = null,
    @Json(name= "email") val email: String? = null,
    @Json(name= "bio") val bio: String? = null,
    @Json(name= "public_repos") val publicReposCount: Int = 0,
    @Json(name= "followers") val followersCount: Int = 0,
    @Json(name= "following") val followingCount: Int = 0,
)