package com.hieulh.test.demoproj2.ui.app.list

import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Modifier
import com.hieulh.test.demoproj2.ui.pojo.SearchUserUIModel
import kotlinx.coroutines.flow.distinctUntilChanged

@Composable
fun ListGithubUser(
    userList: List<SearchUserUIModel>,
    onItemClick: (String) -> Unit,
    onLoadMoreCallback: () -> Unit,
    modifier: Modifier
) {
    val listState = rememberLazyListState()
    LazyColumn(state = listState, modifier = modifier) {
        items(items = userList) { user ->
            ItemGithubUser(user, onItemClick)
        }
    }
    InfiniteListHandler(listState = listState) {
        onLoadMoreCallback()
    }
}

@Composable
fun InfiniteListHandler(
    listState: LazyListState,
    buffer: Int = 2,
    onLoadMore: () -> Unit
) {
    val loadMore = remember {
        derivedStateOf {
            val layoutInfo = listState.layoutInfo
            val totalItemsNumber = layoutInfo.totalItemsCount
            val lastVisibleItemIndex = (layoutInfo.visibleItemsInfo.lastOrNull()?.index ?: 0) + 1

            lastVisibleItemIndex > (totalItemsNumber - buffer)
        }
    }

    LaunchedEffect(loadMore) {
        snapshotFlow { loadMore.value }
            .distinctUntilChanged()
            .collect {
                onLoadMore()
            }
    }
}
