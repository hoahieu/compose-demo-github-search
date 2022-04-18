package com.hieulh.test.demoproj2.data.mapper

import com.hieulh.test.demoproj2.data.pojo.SearchUserEntity
import com.hieulh.test.demoproj2.domain.pojo.SearchUser

class SearchUserEntityMapper() : (SearchUserEntity) -> SearchUser {
    override fun invoke(entity: SearchUserEntity) = SearchUser(
        username = entity.username,
        id = entity.id,
        avatarUrl = entity.avatarUrl
    )
}