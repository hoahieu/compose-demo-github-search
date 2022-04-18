package com.hieulh.test.demoproj2.ui

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.hieulh.test.demoproj2.provider.MainCoroutineScopeRule
import org.junit.Rule

open class BaseViewModelTest {
    @get:Rule
    val instantExecutorRule = InstantTaskExecutorRule()

    @get:Rule
    val coroutineScope = MainCoroutineScopeRule()
}