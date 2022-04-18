package com.hieulh.test.demoproj2.domain.usecase

import com.hieulh.test.demoproj2.common.datastate.DataState
import com.hieulh.test.demoproj2.domain.GithubRepo
import com.hieulh.test.demoproj2.domain.pojo.SearchRequest
import com.hieulh.test.demoproj2.domain.pojo.SearchResult
import com.hieulh.test.demoproj2.domain.pojo.SearchUser
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.flow

class SearchUserUseCase(private val githubRepo: GithubRepo) : FlowUseCase<SearchRequest, DataState<SearchResult<SearchUser>>> {
    override fun execute(parameters: SearchRequest): Flow<DataState<SearchResult<SearchUser>>> = flow {
        emit(DataState.Loading())
        try {
            emit(DataState.Result(githubRepo.searchUser(parameters)))
        } catch (e: Exception) {
            emit(DataState.Error(e))
        }
    }
        .debounce(DEBOUNCE_TIME)
}