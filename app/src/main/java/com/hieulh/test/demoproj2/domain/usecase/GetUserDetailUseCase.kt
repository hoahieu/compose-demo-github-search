package com.hieulh.test.demoproj2.domain.usecase

import com.hieulh.test.demoproj2.common.datastate.DataState
import com.hieulh.test.demoproj2.domain.GithubRepo
import com.hieulh.test.demoproj2.domain.pojo.UserDetail
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class GetUserDetailUseCase(private val githubRepo: GithubRepo) : FlowUseCase<String, DataState<UserDetail>>  {
    override fun execute(username: String): Flow<DataState<UserDetail>> = flow {
        emit(DataState.Loading())
        try {
            emit(DataState.Result(githubRepo.getUserDetail(username)))
        } catch (e: Exception) {
            emit(DataState.Error(e))
        }
    }
}