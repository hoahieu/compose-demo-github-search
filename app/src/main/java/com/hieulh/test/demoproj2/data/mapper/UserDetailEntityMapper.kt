package com.hieulh.test.demoproj2.data.mapper

import com.hieulh.test.demoproj2.data.pojo.UserDetailEntity
import com.hieulh.test.demoproj2.domain.pojo.UserDetail


class UserDetailEntityMapper() : (UserDetailEntity) -> UserDetail {
    override fun invoke(detailEntity: UserDetailEntity): UserDetail {
        return UserDetail(
            username = detailEntity.username,
            id = detailEntity.id,
            avatarUrl = detailEntity.avatarUrl,
            gravatarId = detailEntity.gravatarId,
            url = detailEntity.url,
            htmlUrl = detailEntity.htmlUrl,
            name = detailEntity.name,
            company = detailEntity.company,
            location = detailEntity.location,
            email = detailEntity.email,
            bio = detailEntity.bio,
            publicReposCount = detailEntity.publicReposCount,
            followersCount = detailEntity.followersCount,
            followingCount = detailEntity.followingCount,
        )
    }
}