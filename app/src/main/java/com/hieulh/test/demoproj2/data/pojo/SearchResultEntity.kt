package com.hieulh.test.demoproj2.data.pojo

import com.squareup.moshi.Json

class SearchResultEntity<T>(
    @Json(name = "total_count") val totalCount: Int? = null,
    @Json(name = "items") val items: List<T>? = null
)