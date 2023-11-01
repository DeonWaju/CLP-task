package com.example.taskcdp.domain.usecases

import com.example.taskcdp.data.local.entity.UserProfile

interface UpdateProfileImageRepository {
    suspend operator fun invoke(id: Int, imageUrl: String)
}