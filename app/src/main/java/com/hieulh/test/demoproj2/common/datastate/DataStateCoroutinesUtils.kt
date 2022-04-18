package com.hieulh.test.demoproj2.common.datastate

inline fun <reified T> DataState<T>.tryToGetResultData(): T? = (this as? DataState.Result)?.result