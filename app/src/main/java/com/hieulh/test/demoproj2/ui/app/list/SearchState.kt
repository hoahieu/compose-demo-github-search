package com.hieulh.test.demoproj2.ui.app.list

import com.hieulh.test.demoproj2.common.datastate.DataState
import com.hieulh.test.demoproj2.ui.pojo.SearchUserUIModel

sealed class SearchState {
    object Empty : SearchState()
    data class Searching(val dataState: DataState<List<SearchUserUIModel>>): SearchState()
}