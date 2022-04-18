package com.hieulh.test.demoproj2.domain

import com.hieulh.test.demoproj2.domain.pojo.SearchRequest
import com.hieulh.test.demoproj2.domain.pojo.SearchResult
import com.hieulh.test.demoproj2.domain.pojo.SearchUser
import com.hieulh.test.demoproj2.domain.pojo.UserDetail

interface GithubRepo {
    suspend fun searchUser(searchRequest: SearchRequest): SearchResult<SearchUser>
    suspend fun getUserDetail(username: String): UserDetail
}