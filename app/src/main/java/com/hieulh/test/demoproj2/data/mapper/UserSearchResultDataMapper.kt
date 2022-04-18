package com.hieulh.test.demoproj2.data.mapper

import com.hieulh.test.demoproj2.data.pojo.SearchResultEntity
import com.hieulh.test.demoproj2.data.pojo.SearchUserEntity
import com.hieulh.test.demoproj2.domain.pojo.SearchRequest
import com.hieulh.test.demoproj2.domain.pojo.SearchResult
import com.hieulh.test.demoproj2.domain.pojo.SearchUser

class UserSearchResultDataMapper(
    private val searchUserEntityMapper: (SearchUserEntity) -> SearchUser
) : (SearchResultEntity<SearchUserEntity>, SearchRequest) -> SearchResult<SearchUser> {
    override fun invoke(
        searchResultEntity: SearchResultEntity<SearchUserEntity>,
        searchRequest: SearchRequest
    ): SearchResult<SearchUser> {
        return SearchResult(
            totalCount = searchResultEntity.totalCount ?: 0,
            currentPage = searchRequest.page,
            perPage = searchRequest.perPage,
            items = searchResultEntity.items?.map(searchUserEntityMapper) ?: emptyList()
        )
    }
}

