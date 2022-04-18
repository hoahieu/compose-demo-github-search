package com.hieulh.test.demoproj2.ui.mapper

import com.hieulh.test.demoproj2.domain.pojo.SearchUser
import com.hieulh.test.demoproj2.ui.pojo.SearchUserUIModel

class SearchUserUIMapper : (SearchUser) -> SearchUserUIModel {
    override fun invoke(searchUser: SearchUser) = SearchUserUIModel(
        username = searchUser.username,
        id = searchUser.id,
        avatarUrl = searchUser.avatarUrl
    )
}