package com.hieulh.test.demoproj2.ui.pojo

data class UserDetailUIModel(
    val username: String,
    val id: Int,
    val avatarUrl: String? = null,
    val url: String? = null,
    val name: String? = null,
    val company: String? = null,
    val email: String? = null,
    val bio: String? = null
)