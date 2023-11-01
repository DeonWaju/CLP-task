package com.example.taskcdp.data.repo

import Constants.SOMETHING_WRONG
import Resource
import com.example.taskcdp.data.local.dao.UserProfileDao
import com.example.taskcdp.di.AppDispatchers
import com.example.taskcdp.di.Dispatcher
import com.example.taskcdp.util.SessionManager
import com.example.taskcdp.data.remote.ApiService
import com.example.taskcdp.domain.usecases.AuthLocalRepository
import com.example.taskcdp.data.model.LoginRequest
import com.example.taskcdp.data.model.Responses
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.withContext
import javax.inject.Inject

class AuthLocalImpl @Inject constructor(
    private val sessionManager: SessionManager,
    private val userProfileDao: UserProfileDao,
    @Dispatcher(AppDispatchers.MAIN) private val mainDispatcher: CoroutineDispatcher,
) : AuthLocalRepository {

    override fun getLoginDetails(): Pair<String, String>? = sessionManager.getLoginDetails()
    override fun userIsAuthenticated(): Boolean = sessionManager.isAuth()
    override fun saveUserIsAuthenticated(isAuth: Boolean) = sessionManager.isAuth(isAuth)
    override fun saveLoginDetails(username: String, password: String) =
        sessionManager.saveLoginDetails(username, password)

    override fun getRememberUser(): Boolean = sessionManager.rememberUser()

    override fun saveRememberUser(isChecked: Boolean) = sessionManager.rememberUser(isChecked)

    override fun clearLoginDetails() = sessionManager.clearLoginDetails()

    override fun clearAllData() = sessionManager.clearData()
    override suspend fun clearUserData() = withContext(mainDispatcher) {
        userProfileDao.deleteAll()
    }
}