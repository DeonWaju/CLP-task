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
import com.example.taskcdp.domain.usecases.AuthRemoteRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.withContext
import javax.inject.Inject

class AuthRemoteImpl @Inject constructor(
    private val api: ApiService,
    @Dispatcher(AppDispatchers.IO) private val ioDispatcher: CoroutineDispatcher,
) : AuthRemoteRepository {
    override fun invoke(loginRequest: LoginRequest): Flow<Resource<Responses.LoginUserDataResponse>> =
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
}