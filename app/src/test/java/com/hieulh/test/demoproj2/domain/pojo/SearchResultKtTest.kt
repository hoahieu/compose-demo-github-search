package com.hieulh.test.demoproj2.domain.pojo

import org.junit.Assert.*

import org.junit.Test

class SearchResultKtTest {

    @Test
    fun hasMoreData() {
        assertTrue(
            SearchResult(
                totalCount = 11,
                currentPage = 3,
                perPage = 3,
                items = Array(3) { "" }.toList(),
            ).hasMoreData()
        )
        assertTrue(
            SearchResult(
                totalCount = 12,
                currentPage = 3,
                perPage = 3,
                items = Array(3) { "" }.toList(),
            ).hasMoreData()
        )

        assertFalse(
            SearchResult(
                totalCount = 12,
                currentPage = 4,
                perPage = 3,
                items = Array(3) { "" }.toList(),
            ).hasMoreData()
        )

        assertFalse(
            SearchResult(
                totalCount = 11,
                currentPage = 4,
                perPage = 3,
                items = Array(3) { "" }.toList(),
            ).hasMoreData()
        )

        assertFalse(
            SearchResult(
                totalCount = 10,
                currentPage = 4,
                perPage = 3,
                items = Array(3) { "" }.toList(),
            ).hasMoreData()
        )
    }
}