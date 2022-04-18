package com.hieulh.test.demoproj2.domain.pojo

data class UserDetail(
    val username: String,
    val id: Int,
    val avatarUrl: String? = null,
    val gravatarId: String? = null,
    val url: String? = null,
    val htmlUrl: String? = null,
    val name: String? = null,
    val company: String? = null,
    val location: String? = null,
    val email: String? = null,
    val bio: String? = null,
    val publicReposCount: Int = 0,
    val followersCount: Int = 0,
    val followingCount: Int = 0,
)