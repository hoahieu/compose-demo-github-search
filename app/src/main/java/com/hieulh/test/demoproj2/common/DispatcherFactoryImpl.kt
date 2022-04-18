package com.hieulh.test.demoproj2.common

import kotlinx.coroutines.Dispatchers

class DispatcherFactoryImpl: DispatcherFactory {
    override fun io() = Dispatchers.IO
    override fun main() = Dispatchers.Main
    override fun default() = Dispatchers.Default
    override fun unconfined() = Dispatchers.Unconfined
}