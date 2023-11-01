package com.example.taskcdp.domain.usecases

import com.example.taskcdp.data.local.entity.UserProfile

interface SaveUserProfileRepository {
    suspend operator fun invoke(userProfile: UserProfile)
}