package com.example.taskcdp.data.repo

import com.example.taskcdp.data.local.dao.UserProfileDao
import com.example.taskcdp.data.local.entity.UserProfile
import com.example.taskcdp.domain.usecases.SaveUserProfileRepository
import com.example.taskcdp.domain.usecases.UpdateProfileImageRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import javax.inject.Inject

class UpdateProfileImageImpl @Inject constructor(
    private val userProfileDao: UserProfileDao,
    private val ioDispatcher: CoroutineDispatcher
): UpdateProfileImageRepository{
    override suspend fun invoke(id: Int, imageUrl: String) = withContext(ioDispatcher){
        userProfileDao.updateUserImage(id, imageUrl)
    }
}