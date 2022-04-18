package com.hieulh.test.demoproj2.domain.pojo

class SearchResult<T>(
    val totalCount: Int,
    val currentPage: Int,
    val perPage: Int,
    val items: List<T> = emptyList()
)

fun SearchResult<*>.hasMoreData() =
    this.currentPage < (totalCount / perPage + if (totalCount.rem(perPage) == 0) 0 else 1)