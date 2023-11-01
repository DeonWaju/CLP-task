package com.example.taskcdp.data.repo

import Constants.SOMETHING_WRONG
import Resource
import com.example.taskcdp.data.local.dao.UserProfileDao
import com.example.taskcdp.di.AppDispatchers
import com.example.taskcdp.di.Dispatcher
import com.example.taskcdp.util.SessionManager
import com.example.taskcdp.data.remote.ApiService
import com.example.taskcdp.domain.usecases.AuthRepository
import com.example.taskcdp.data.model.LoginRequest
import com.example.taskcdp.data.model.Responses
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.withContext
import javax.inject.Inject

class AuthImpl @Inject constructor(
    private val api: ApiService,
    private val sessionManager: SessionManager,
    private val userProfileDao: UserProfileDao,
    @Dispatcher(AppDispatchers.IO) private val ioDispatcher: CoroutineDispatcher,
) : AuthRepository {
    override fun loginUser(loginRequest: LoginRequest): Flow<Resource<Responses.LoginUserDataResponse>> =
        flow {
            emit(Resource.Loading())

            val remoteData = try {
                api.login(loginRequest)
            } catch (e: Exception) {
                emit(Resource.Error(message = e.message ?: SOMETHING_WRONG))
                null
            }

            remoteData?.let {
                emit(Resource.Success(it))
            }
        }.flowOn(ioDispatcher)

    override fun getLoginDetails(): Pair<String, String>? = sessionManager.getLoginDetails()
    override fun userIsAuthenticated(): Boolean = sessionManager.isAuth()
    override fun saveUserIsAuthenticated(isAuth: Boolean) = sessionManager.isAuth(isAuth)
    override fun saveLoginDetails(username: String, password: String) =
        sessionManager.saveLoginDetails(username, password)

    override fun getRememberUser(): Boolean = sessionManager.rememberUser()

    override fun saveRememberUser(isChecked: Boolean) = sessionManager.rememberUser(isChecked)

    override fun clearLoginDetails() = sessionManager.clearLoginDetails()

    override fun clearAllData() = sessionManager.clearData()
    override suspend fun clearUserData() = withContext(ioDispatcher) {
        userProfileDao.deleteAll()
    }
}