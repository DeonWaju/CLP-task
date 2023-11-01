package com.example.taskcdp.domain.usecases

import Resource
import com.example.taskcdp.data.model.LoginRequest
import com.example.taskcdp.data.model.Responses
import kotlinx.coroutines.flow.Flow

interface AuthRepository {

    fun loginUser(loginRequest: LoginRequest): Flow<Resource<Responses.LoginUserDataResponse>>
    fun getLoginDetails(): Pair<String, String>?
    fun userIsAuthenticated(): Boolean
    fun saveUserIsAuthenticated(isAuth: Boolean)
    fun saveLoginDetails(username: String, password: String)
    fun getRememberUser(): Boolean
    fun saveRememberUser(isChecked: Boolean)
    fun clearLoginDetails()
    fun clearAllData()
}