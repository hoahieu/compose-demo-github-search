package com.hieulh.test.demoproj2.ui.app.detail

import com.hieulh.test.demoproj2.TestDispatcherFactoryImpl
import com.hieulh.test.demoproj2.common.datastate.DataState
import com.hieulh.test.demoproj2.domain.pojo.UserDetail
import com.hieulh.test.demoproj2.domain.usecase.GetUserDetailUseCase
import com.hieulh.test.demoproj2.ui.BaseViewModelTest
import com.hieulh.test.demoproj2.ui.mapper.UserDetailUIMapper
import com.hieulh.test.demoproj2.ui.pojo.UserDetailUIModel
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test

class DetailViewModelTest : BaseViewModelTest() {
    private val getUserDetailUseCase = mockk<GetUserDetailUseCase>()
    private val userDetailUIMapper = UserDetailUIMapper()

    lateinit var viewModel: DetailViewModel

    @Before
    fun setup() {
        every { getUserDetailUseCase.execute(any()) } returns flowOf(
            DataState.Result(
                UserDetail(
                    username = "Octocat",
                    id = 583231
                )
            )
        )

        viewModel = DetailViewModel(
            TestDispatcherFactoryImpl(),
            getUserDetailUseCase,
            userDetailUIMapper
        )
    }


    @Test
    fun getUserDetailFlow() {
        runBlocking {
            viewModel.getUserDetail("Octocat")
            assertEquals(
                DataState.Result(
                    UserDetailUIModel(
                        username = "Octocat",
                        id = 583231
                    )
                ), viewModel.userDetailFlow.first()
            )
        }
    }
}