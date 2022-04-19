package com.hieulh.test.demoproj2.ui.app.list

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.hieulh.test.demoproj2.common.DispatcherFactory
import com.hieulh.test.demoproj2.common.datastate.DataState
import com.hieulh.test.demoproj2.common.datastate.isLoading
import com.hieulh.test.demoproj2.common.datastate.isResult
import com.hieulh.test.demoproj2.common.datastate.mapData
import com.hieulh.test.demoproj2.common.datastate.onResult
import com.hieulh.test.demoproj2.domain.pojo.SearchRequest
import com.hieulh.test.demoproj2.domain.pojo.SearchResult
import com.hieulh.test.demoproj2.domain.pojo.SearchUser
import com.hieulh.test.demoproj2.domain.pojo.hasMoreData
import com.hieulh.test.demoproj2.domain.usecase.SearchUserUseCase
import com.hieulh.test.demoproj2.ui.pojo.SearchUserUIModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch

class SearchViewModel(
    private val dispatcherFactory: DispatcherFactory,
    private val searchUserUseCase: SearchUserUseCase,
    private val searchUserUIMapper: (SearchUser) -> SearchUserUIModel,
) : ViewModel() {
    companion object {
        const val QUERY_DEBOUNCE_TIME = 500L
    }

    private val _searchUserFlow = MutableSharedFlow<DataState<SearchResult<SearchUser>>>(replay = 1)
    private val userList = mutableListOf<SearchUser>()
    private var currentPage: Int? = null
    private var hasMoreData: Boolean = false

    private var query: String? = null
    private val _searchStateFlow = MutableStateFlow<SearchState>(SearchState.Empty)

    val searchStateFlow = _searchStateFlow.asStateFlow()

    private val _queryFlow = MutableSharedFlow<String>(replay = 1)

    init {
        viewModelScope.launch(dispatcherFactory.io()) {
            _searchUserFlow
                .onEach { it ->
                    it.onResult {
                        if (it.currentPage == 1) userList.clear()
                        userList.addAll(it.items)
                        currentPage = it.currentPage
                        hasMoreData = it.hasMoreData()
                    }
                }
                .map { dataState -> dataState.mapData { it.items.map(searchUserUIMapper) } }
                .map {
                    when {
                        it.isLoading() && userList.isNotEmpty() -> DataState.Loading(userList.map(searchUserUIMapper))
                        it.isResult() -> DataState.Result(userList.map(searchUserUIMapper))
                        else -> it
                    }
                }
                .collect {
                    _searchStateFlow.tryEmit(SearchState.Searching(it))
                }
        }

        viewModelScope.launch {
            _queryFlow
                .debounce(QUERY_DEBOUNCE_TIME)
                .distinctUntilChanged()
                .collect(::searchUser)
        }
    }

    fun submitQuery(query: String) {
        val formattedQuery = formatQuery(query)
        this.query = formattedQuery
        currentPage = null
        userList.clear()
        viewModelScope.launch {
            _queryFlow.tryEmit(formattedQuery)
        }
    }

    fun loadMore() {
        if (!hasMoreData) return // skip if there's no more data
        if (currentPage == null) return // skip if current page is not specified
        if (_searchUserFlow.replayCache.lastOrNull()?.isLoading() == true) return // skip if loading
        if (formatQuery(query).isEmpty()) return // skip if query is empty

        viewModelScope.launch(dispatcherFactory.io()) {
            currentPage?.let { page ->
                searchUserUseCase.execute(
                    SearchRequest(
                        query = formatQuery(query),
                        page = page + 1
                    )
                )
                    .flowOn(dispatcherFactory.io())
                    .collect(_searchUserFlow::tryEmit)
            }

        }

    }

    private fun searchUser(formattedQuery: String) {
        viewModelScope.launch(dispatcherFactory.io()) {
            if (formattedQuery.isEmpty()) {
                _searchStateFlow.tryEmit(SearchState.Empty)
            } else {
                searchUserUseCase.execute(SearchRequest(query = formattedQuery))
                    .flowOn(dispatcherFactory.io())
                    .collect(_searchUserFlow::tryEmit)
            }
        }
    }

    private fun formatQuery(query: String?): String = query?.trim()?.lowercase().orEmpty()
}