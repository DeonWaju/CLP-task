package com.example.taskcdp.domain.usecases

import Resource
import com.example.taskcdp.data.model.LoginRequest
import com.example.taskcdp.data.model.Responses
import kotlinx.coroutines.flow.Flow

interface AuthRemoteRepository {
    operator fun invoke(loginRequest: LoginRequest): Flow<Resource<Responses.LoginUserDataResponse>>
}