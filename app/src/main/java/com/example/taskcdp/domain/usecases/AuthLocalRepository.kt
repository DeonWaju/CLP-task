package com.example.taskcdp.domain.usecases

import Resource
import com.example.taskcdp.data.model.LoginRequest
import com.example.taskcdp.data.model.Responses
import kotlinx.coroutines.flow.Flow

interface AuthLocalRepository {
    fun getLoginDetails(): Pair<String, String>?
    fun saveLoginDetails(username: String, password: String)

    fun userIsAuthenticated(): Boolean

    fun saveUserIsAuthenticated(isAuth: Boolean)

    fun getRememberUser(): Boolean

    fun saveRememberUser(isChecked: Boolean)

    fun clearLoginDetails()

    fun clearAllData()

    suspend fun clearUserData()
}