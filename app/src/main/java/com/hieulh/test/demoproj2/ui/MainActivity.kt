package com.hieulh.test.demoproj2.ui

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material.DrawerValue
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.material.rememberDrawerState
import androidx.compose.material.rememberScaffoldState
import androidx.compose.ui.res.stringResource
import com.hieulh.test.demoproj2.R
import com.hieulh.test.demoproj2.ui.app.GithubSearchApp
import com.hieulh.test.demoproj2.ui.app.GithubSearchAppScaffold
import com.hieulh.test.demoproj2.ui.app.detail.DetailViewModel
import com.hieulh.test.demoproj2.ui.app.list.SearchViewModel
import com.hieulh.test.demoproj2.ui.theme.DemoProj2Theme
import org.koin.androidx.viewmodel.ext.android.viewModel

class MainActivity : ComponentActivity() {
    private val searchViewModel: SearchViewModel by viewModel()
    private val detailViewModel: DetailViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            DemoProj2Theme {
                val scaffoldState = rememberScaffoldState(rememberDrawerState(DrawerValue.Open))
                GithubSearchAppScaffold(
                    scaffoldState = scaffoldState,
                    topBar = {
                        TopAppBar(title = {
                            Text(text = stringResource(R.string.app_name))
                        })
                    }
                ) {
                    GithubSearchApp(searchViewModel, detailViewModel)
                }
            }
        }
    }
}