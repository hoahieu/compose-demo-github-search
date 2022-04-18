package com.hieulh.test.demoproj2.data

import com.hieulh.test.demoproj2.data.api.GithubApiService
import com.hieulh.test.demoproj2.data.pojo.SearchResultEntity
import com.hieulh.test.demoproj2.data.pojo.SearchUserEntity
import com.hieulh.test.demoproj2.data.pojo.UserDetailEntity
import com.hieulh.test.demoproj2.domain.GithubRepo
import com.hieulh.test.demoproj2.domain.pojo.SearchRequest
import com.hieulh.test.demoproj2.domain.pojo.SearchResult
import com.hieulh.test.demoproj2.domain.pojo.SearchUser
import com.hieulh.test.demoproj2.domain.pojo.UserDetail

class GithubRepoImpl(
    private val githubApiService: GithubApiService,
    private val searchResultMapper: (SearchResultEntity<SearchUserEntity>, SearchRequest) -> SearchResult<SearchUser>,
    private val userDetailMapper: (UserDetailEntity) -> UserDetail
) : GithubRepo {
    override suspend fun searchUser(searchRequest: SearchRequest): SearchResult<SearchUser> = searchResultMapper.invoke(
        githubApiService.searchUser(searchRequest.query, searchRequest.page),
        searchRequest
    )

    override suspend fun getUserDetail(username: String): UserDetail = userDetailMapper.invoke(
        githubApiService.getUserDetail(username)
    )
}