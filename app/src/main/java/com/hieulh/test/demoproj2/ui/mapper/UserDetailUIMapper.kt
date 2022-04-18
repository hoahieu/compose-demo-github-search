package com.hieulh.test.demoproj2.ui.mapper

import com.hieulh.test.demoproj2.domain.pojo.UserDetail
import com.hieulh.test.demoproj2.ui.pojo.UserDetailUIModel

class UserDetailUIMapper : (UserDetail) -> UserDetailUIModel {
    override fun invoke(userDetail: UserDetail) = UserDetailUIModel(
        username = userDetail.username,
        id = userDetail.id,
        avatarUrl = userDetail.avatarUrl,
        url = userDetail.url,
        name = userDetail.name,
        company = userDetail.company,
        email = userDetail.email,
        bio = userDetail.bio
    )
}