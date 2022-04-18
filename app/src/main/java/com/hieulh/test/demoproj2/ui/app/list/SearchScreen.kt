package com.hieulh.test.demoproj2.ui.app.list

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.LinearProgressIndicator
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Alignment.Companion.CenterHorizontally
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.hieulh.test.demoproj2.R
import com.hieulh.test.demoproj2.common.datastate.DataState
import com.hieulh.test.demoproj2.common.datastate.extractData
import com.hieulh.test.demoproj2.common.datastate.isError
import com.hieulh.test.demoproj2.common.datastate.isLoading
import com.hieulh.test.demoproj2.common.datastate.tryToGetResultData

@Composable
fun SearchScreen(
    searchViewModel: SearchViewModel,
    openItemCallback: (String) -> Unit
) {
    val searchDataState = searchViewModel.searchStateFlow.collectAsState(initial = SearchState.Empty)
    Column() {
        Row(
            Modifier
                .height(64.dp)
                .fillMaxWidth()
                .padding(16.dp, 0.dp, 0.dp, 0.dp)
        ) {
            var query by rememberSaveable { mutableStateOf("") }
            LaunchedEffect(query) {
                searchViewModel.submitQuery(query)
            }
            OutlinedTextField(
                value = query,
                onValueChange = {
                    query = it
                },
                label = { Text(stringResource(R.string.list_search_label)) },
                singleLine = true,
                modifier = Modifier
                    .fillMaxHeight()
                    .weight(1f)
            )
            Image(
                painter = painterResource(R.drawable.ic_baseline_clear_24),
                contentScale = ContentScale.Inside,
                contentDescription = "",
                modifier = Modifier
                    .size(56.dp)
                    .align(Alignment.Bottom)
                    .clickable { query = "" }
            )
        }

        (searchDataState.value as? SearchState.Searching)?.dataState?.run {
            when {
                extractData().isNullOrEmpty().not() -> ListGithubUser(
                    userList = extractData()!!,
                    onItemClick = openItemCallback,
                    onLoadMoreCallback = searchViewModel::loadMore,
                    modifier = Modifier.fillMaxSize()
                )
                isError() -> showError()
                isLoading() -> showLoading()
                tryToGetResultData()?.isEmpty() == true -> showEmptyList()
            }
            if (this is DataState.Loading && partialData != null) {
                LinearProgressIndicator(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(8.dp)
                )
            }
        } ?: run { showEmptyState() }
    }
}

@Composable
private fun showEmptyState() = showMessage(message = stringResource(R.string.list_search_suggest))

@Composable
private fun showError() = showMessage(message = stringResource(R.string.error_general))

@Composable
private fun showEmptyList() = showMessage(message = stringResource(R.string.list_empty))

@Composable
private fun ColumnScope.showLoading() {
    Spacer(modifier = Modifier.height(64.dp))
    CircularProgressIndicator(modifier = Modifier
        .fillMaxSize(0.2f)
        .align(CenterHorizontally))
}


@Composable
private fun showMessage(message: String) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        contentAlignment = Alignment.TopCenter
    ) {
        Text(text = message, modifier = Modifier.fillMaxWidth())
    }
}