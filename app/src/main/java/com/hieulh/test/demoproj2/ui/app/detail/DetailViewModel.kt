package com.hieulh.test.demoproj2.ui.app.detail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.hieulh.test.demoproj2.common.DispatcherFactory
import com.hieulh.test.demoproj2.common.datastate.DataState
import com.hieulh.test.demoproj2.common.datastate.mapData
import com.hieulh.test.demoproj2.domain.pojo.UserDetail
import com.hieulh.test.demoproj2.domain.usecase.GetUserDetailUseCase
import com.hieulh.test.demoproj2.ui.pojo.UserDetailUIModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch

class DetailViewModel(
    private val dispatcherFactory: DispatcherFactory,
    private val getUserDetailUseCase: GetUserDetailUseCase,
    private val userDetailUIMapper: (UserDetail) -> UserDetailUIModel
) : ViewModel() {
    private val _userDetailFlow = MutableSharedFlow<DataState<UserDetail>>(replay = 1)
    val userDetailFlow = _userDetailFlow.map { it.mapData(userDetailUIMapper) }

    fun getUserDetail(username: String) {
        viewModelScope.launch(dispatcherFactory.io()) {
            getUserDetailUseCase.execute(username)
                .flowOn(dispatcherFactory.io())
                .collect(_userDetailFlow::tryEmit)
        }
    }
}