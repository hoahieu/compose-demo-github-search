package com.hieulh.test.demoproj2.domain.usecase

import kotlinx.coroutines.flow.Flow

const val DEBOUNCE_TIME = 100L // skip loading if exception is thrown too soon
interface FlowUseCase<In, Out> {
    fun execute(parameters: In): Flow<Out>
}