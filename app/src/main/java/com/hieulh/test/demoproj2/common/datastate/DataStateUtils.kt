package com.hieulh.test.demoproj2.common.datastate

inline fun <reified T> DataState<T>.onResult(onResult: (result: T) -> Unit): DataState<T> {
    if (this is DataState.Result) onResult(result)
    return this
}


inline fun <reified T, reified Y> DataState<T>.mapData(transform: (T) -> Y): DataState<Y> = try {
    when (this) {
        is DataState.Result<T> -> DataState.Result(transform(result))
        is DataState.Loading<T> -> DataState.Loading(partialData?.let { transform(it) })
        is DataState.Error<T> -> DataState.Error(throwable)
    }
} catch (e: Throwable) {
    DataState.Error(e)
}

inline fun <reified T> DataState<T>.extractData() = when (this) {
    is DataState.Result -> result
    is DataState.Loading -> partialData
    is DataState.Error -> null
}

fun DataState<*>.isLoading() = this is DataState.Loading
fun DataState<*>.isResult() = this is DataState.Result
fun DataState<*>.isError() = this is DataState.Error
