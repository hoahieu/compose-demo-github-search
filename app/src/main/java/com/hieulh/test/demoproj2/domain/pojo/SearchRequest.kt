package com.hieulh.test.demoproj2.domain.pojo

data class SearchRequest(
    val query: String,
    val page: Int = 0, // start from 1
    val perPage: Int = DEFAULT_PER_PAGE
) {
    companion object {
        const val DEFAULT_PER_PAGE = 30
    }
}