package com.hieulh.test.demoproj2.common.datastate

sealed class DataState<T> {
    data class Result<T>(val result: T) : DataState<T>()
    data class Loading<T>(val partialData: T? = null) : DataState<T>()
    data class Error<T>(val throwable: Throwable) : DataState<T>()
}