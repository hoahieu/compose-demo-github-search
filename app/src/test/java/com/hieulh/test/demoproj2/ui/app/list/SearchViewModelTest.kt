package com.hieulh.test.demoproj2.ui.app.list

import com.hieulh.test.demoproj2.TestDispatcherFactoryImpl
import com.hieulh.test.demoproj2.common.datastate.DataState
import com.hieulh.test.demoproj2.domain.pojo.SearchResult
import com.hieulh.test.demoproj2.domain.pojo.SearchUser
import com.hieulh.test.demoproj2.domain.usecase.SearchUserUseCase
import com.hieulh.test.demoproj2.ui.BaseViewModelTest
import com.hieulh.test.demoproj2.ui.mapper.SearchUserUIMapper
import com.hieulh.test.demoproj2.ui.pojo.SearchUserUIModel
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.withTimeoutOrNull
import org.junit.Assert.*
import org.junit.Before

import org.junit.Test

class SearchViewModelTest : BaseViewModelTest() {
    private val searchUserUseCase = mockk<SearchUserUseCase>()
    val searchUserUIMapper = SearchUserUIMapper()

    lateinit var viewModel: SearchViewModel

    @Before
    fun setup() {
        every { searchUserUseCase.execute(any()) } returns flowOf(
            DataState.Result(
                SearchResult<SearchUser>(
                    totalCount = 3,
                    currentPage = 1,
                    perPage = 1,
                    items = listOf(
                        SearchUser(
                            username = "Octocat",
                            id = 583231
                        )
                    )
                )
            )
        )
        viewModel = SearchViewModel(
            TestDispatcherFactoryImpl(),
            searchUserUseCase,
            searchUserUIMapper
        )
    }

    @Test
    fun submitQuery() {
        runTest {
            viewModel.submitQuery("Octo")
            assertEquals(
                SearchState.Empty,
                viewModel.searchStateFlow.first()
            )
        }
        val output = mutableListOf<SearchState>()

        runTest {
            delay(300L)
            withTimeoutOrNull(1000L) {
                viewModel.submitQuery("Octo")
                viewModel.searchStateFlow.toList(output)
            }
        }
        assertEquals(
            listOf(
                SearchState.Searching(
                    DataState.Result(
                        listOf(SearchUserUIModel(username = "Octocat", id = 583231))
                    )
                )
            ),
            output
        )
    }

    @Test
    fun loadMore() {
        val output = mutableListOf<SearchState>()
        runTest {
            withTimeoutOrNull(2000) {
                viewModel.submitQuery("Octo")
                delay(500)
                viewModel.loadMore()
                delay(500)
                viewModel.searchStateFlow.toList(output)
            }
        }
        assertEquals(
            listOf(
                SearchState.Searching(
                    DataState.Result(
                        listOf(SearchUserUIModel(username = "Octocat", id = 583231))
                    )
                )
            ),
            output
        )
    }
}