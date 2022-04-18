package com.hieulh.test.demoproj2.di

import com.hieulh.test.demoproj2.BuildConfig
import com.hieulh.test.demoproj2.common.DispatcherFactory
import com.hieulh.test.demoproj2.common.DispatcherFactoryImpl
import com.hieulh.test.demoproj2.data.GithubRepoImpl
import com.hieulh.test.demoproj2.data.api.ApiConfig.CONNECT_TIMEOUT
import com.hieulh.test.demoproj2.data.api.ApiConfig.GITHUB_API
import com.hieulh.test.demoproj2.data.api.ApiConfig.READ_TIMEOUT
import com.hieulh.test.demoproj2.data.api.ApiConfig.WRITE_TIMEOUT
import com.hieulh.test.demoproj2.data.api.GithubApiService
import com.hieulh.test.demoproj2.data.mapper.SearchUserEntityMapper
import com.hieulh.test.demoproj2.data.mapper.UserDetailEntityMapper
import com.hieulh.test.demoproj2.data.mapper.UserSearchResultDataMapper
import com.hieulh.test.demoproj2.data.pojo.SearchResultEntity
import com.hieulh.test.demoproj2.data.pojo.SearchUserEntity
import com.hieulh.test.demoproj2.data.pojo.UserDetailEntity
import com.hieulh.test.demoproj2.domain.GithubRepo
import com.hieulh.test.demoproj2.domain.pojo.SearchRequest
import com.hieulh.test.demoproj2.domain.pojo.SearchResult
import com.hieulh.test.demoproj2.domain.pojo.SearchUser
import com.hieulh.test.demoproj2.domain.pojo.UserDetail
import com.hieulh.test.demoproj2.domain.usecase.GetUserDetailUseCase
import com.hieulh.test.demoproj2.domain.usecase.SearchUserUseCase
import com.hieulh.test.demoproj2.ui.app.detail.DetailViewModel
import com.hieulh.test.demoproj2.ui.app.list.SearchViewModel
import com.hieulh.test.demoproj2.ui.mapper.SearchUserUIMapper
import com.hieulh.test.demoproj2.ui.mapper.UserDetailUIMapper
import com.hieulh.test.demoproj2.ui.pojo.SearchUserUIModel
import com.hieulh.test.demoproj2.ui.pojo.UserDetailUIModel
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import okhttp3.Cache
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.android.ext.koin.androidApplication
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.qualifier.named
import org.koin.core.scope.Scope
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import java.util.concurrent.TimeUnit

const val MAPPER_USER_DETAIL = "user_detail"
const val MAPPER_SEARCH_USER = "search_user"
const val MAPPER_SEARCH_RESULT = "search_result"
const val UI_MAPPER_SEARCH_USER = "ui_search_user"
const val UI_MAPPER_USER_DETAIL = "ui_user_detail"

val appModules = module {
    single<DispatcherFactory> { DispatcherFactoryImpl() }

    // Data
    single { Cache(androidApplication().cacheDir, 10L * 1024 * 1024) } // caching
    single { buildOkHttpClient() }
    single { buildMoshi() }
    single { buildRetrofit() }
    single<GithubApiService> { get<Retrofit>().create(GithubApiService::class.java) }
    single<(UserDetailEntity) -> UserDetail>(named(MAPPER_USER_DETAIL)) { UserDetailEntityMapper() }
    single<(SearchUserEntity) -> SearchUser>(named(MAPPER_SEARCH_USER)) { SearchUserEntityMapper() }
    single<(SearchResultEntity<SearchUserEntity>, SearchRequest) -> SearchResult<SearchUser>>(named(MAPPER_SEARCH_RESULT)) {
        UserSearchResultDataMapper(get(named(MAPPER_SEARCH_USER)))
    }

    // Domain
    factory<GithubRepo> { GithubRepoImpl(get(), get(named(MAPPER_SEARCH_RESULT)), get(named(MAPPER_USER_DETAIL))) }

    // UI
    single<(SearchUser) -> SearchUserUIModel>(named(UI_MAPPER_SEARCH_USER)) { SearchUserUIMapper() }
    single<(UserDetail) -> UserDetailUIModel>(named(UI_MAPPER_USER_DETAIL)) { UserDetailUIMapper() }
    factory { SearchUserUseCase(get()) }
    factory { GetUserDetailUseCase(get()) }
    viewModel { SearchViewModel(get(), get(), get(named(UI_MAPPER_SEARCH_USER))) }
    viewModel { DetailViewModel(get(), get(), get(named(UI_MAPPER_USER_DETAIL))) }
}

private fun Scope.buildOkHttpClient(): OkHttpClient = OkHttpClient.Builder()
    .cache(get())
    .connectTimeout(CONNECT_TIMEOUT, TimeUnit.SECONDS)
    .writeTimeout(WRITE_TIMEOUT, TimeUnit.SECONDS)
    .readTimeout(READ_TIMEOUT, TimeUnit.SECONDS)
    .addInterceptor(
        HttpLoggingInterceptor().apply {
            level = if (BuildConfig.DEBUG) HttpLoggingInterceptor.Level.BODY else HttpLoggingInterceptor.Level.NONE
        })
    .build()

private fun Scope.buildRetrofit(): Retrofit = Retrofit.Builder()
    .client(get())
    .addConverterFactory(MoshiConverterFactory.create(get()))
    .baseUrl(GITHUB_API)
    .build()

private fun Scope.buildMoshi(): Moshi = Moshi.Builder()
    .addLast(KotlinJsonAdapterFactory())
    .build()