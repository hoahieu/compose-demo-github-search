package com.hieulh.test.demoproj2.ui.app.detail

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Alignment.Companion.CenterHorizontally
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.imageResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.hieulh.test.demoproj2.R
import com.hieulh.test.demoproj2.common.datastate.DataState
import com.hieulh.test.demoproj2.ui.pojo.UserDetailUIModel
import com.skydoves.landscapist.glide.GlideImage

@Composable
fun DetailScreen(detailViewModel: DetailViewModel, username: String, navController: NavController) {
    val userDetailState = detailViewModel.userDetailFlow.collectAsState(initial = DataState.Loading())
    LaunchedEffect(userDetailState) {
        detailViewModel.getUserDetail(username)
    }
    Column(
        Modifier
            .fillMaxSize()
            .padding(16.dp)) {
        userDetailState.value.run {
            when (this) {
                is DataState.Loading -> showLoading()
                is DataState.Error -> showError()
                is DataState.Result -> showUser(this.result)
            }
        }
    }
}

@Composable
fun ColumnScope.showLoading() {
    Row(
        horizontalArrangement = Arrangement.Center,
        modifier = Modifier
            .fillMaxSize()
            .align(CenterHorizontally)
    ) {
        CircularProgressIndicator(
            modifier = Modifier
                .size(64.dp)
                .align(Alignment.CenterVertically)
        )
    }
}

@Composable
fun showError() {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
            .height(120.dp)
    ) {
        Text(
            text = stringResource(id = R.string.error_general), modifier = Modifier
                .fillMaxWidth()
                .align(Alignment.Center)
        )
    }
}

@Composable
fun ColumnScope.showUser(user: UserDetailUIModel) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
            .height(160.dp)
    ) {
        GlideImage(
            imageModel = user.avatarUrl,
            contentScale = ContentScale.Crop,
            placeHolder = ImageBitmap.imageResource(R.drawable.ic_image_placeholder_loading),
            error = ImageBitmap.imageResource(R.drawable.ic_image_placeholder_error),
            modifier = Modifier.size(120.dp)
        )
    }
    Text(
        text = user.username,
        modifier = Modifier
            .fillMaxWidth()
            .height(56.dp)
    )
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .weight(1.0f)
    ) {
        user.name?.let { Info(stringResource(R.string.detail_name), it) }
        user.bio?.let { Info(stringResource(R.string.detail_bio), it) }
        user.company?.let { Info(stringResource(R.string.detail_company), it) }
        user.email?.let { Info(stringResource(R.string.detail_email), it) }
        user.url?.let { Info(stringResource(R.string.detail_url), it) }
    }
}

@Composable
fun ColumnScope.Info(key: String, value: String) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(56.dp)
    ) {
        Text(text = key, modifier = Modifier.width(100.dp))
        Spacer(modifier = Modifier.width(16.dp))
        Text(text = value, modifier = Modifier.weight(1.0f))
    }
}