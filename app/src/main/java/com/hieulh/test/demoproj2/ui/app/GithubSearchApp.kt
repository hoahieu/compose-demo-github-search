package com.hieulh.test.demoproj2.ui.app

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.hieulh.test.demoproj2.ui.app.detail.DetailScreen
import com.hieulh.test.demoproj2.ui.app.detail.DetailViewModel
import com.hieulh.test.demoproj2.ui.app.list.SearchScreen
import com.hieulh.test.demoproj2.ui.app.list.SearchViewModel

const val NAV_LIST = "list"
const val NAV_DETAIL = "detail"
const val PARAM_USERNAME = "username"

@Composable
fun GithubSearchApp(searchViewModel: SearchViewModel, detailViewModel: DetailViewModel) {

    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = "list") {
        composable(NAV_LIST) {
            SearchScreen(searchViewModel) { username ->
                navController.navigate("$NAV_DETAIL/$username") {
                    popUpTo(NAV_LIST)
                }
            }
        }
        composable("$NAV_DETAIL/{$PARAM_USERNAME}") {
            DetailScreen(detailViewModel, it.arguments?.getString(PARAM_USERNAME).orEmpty(), navController)
        }
    }
}