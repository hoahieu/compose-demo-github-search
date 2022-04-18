package com.hieulh.test.demoproj2

import com.hieulh.test.demoproj2.common.DispatcherFactory
import kotlinx.coroutines.test.TestCoroutineDispatcher

class TestDispatcherFactoryImpl : DispatcherFactory {
    override fun io() = TestCoroutineDispatcher()

    override fun default() = TestCoroutineDispatcher()

    override fun main() = TestCoroutineDispatcher()

    override fun unconfined() = TestCoroutineDispatcher()
}
