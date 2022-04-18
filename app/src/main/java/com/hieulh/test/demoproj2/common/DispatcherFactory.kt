package com.hieulh.test.demoproj2.common

import kotlinx.coroutines.CoroutineDispatcher

interface DispatcherFactory {
    fun io(): CoroutineDispatcher
    fun main(): CoroutineDispatcher
    fun default(): CoroutineDispatcher
    fun unconfined(): CoroutineDispatcher
}