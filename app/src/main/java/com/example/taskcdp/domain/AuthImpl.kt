package com.example.taskcdp.domain

import com.example.taskcdp.di.AppDispatchers
import com.example.taskcdp.di.Dispatcher
import com.example.taskcdp.util.SessionManager
import com.example.taskcdp.data.ApiService
import com.example.taskcdp.data.AuthRepository
import kotlinx.coroutines.CoroutineDispatcher
import javax.inject.Inject

class AuthImpl @Inject constructor(
    private val api: ApiService,
    private val sessionManager: SessionManager,
    @Dispatcher(AppDispatchers.IO) private val ioDispatcher: CoroutineDispatcher
) : AuthRepository {

    override fun getLoginDetails(): Pair<String, String>? = sessionManager.getLoginDetails()

    override fun saveLoginDetails(username: String, password: String) = sessionManager.saveLoginDetails(username, password)

    override fun getRememberUser(): Boolean = sessionManager.rememberUser()

    override fun saveRememberUser(isChecked: Boolean) = sessionManager.rememberUser(isChecked)

    override fun clearLoginDetails() = sessionManager.clearLoginDetails()

    override fun clearAllData() = sessionManager.clearData()

    override suspend fun login(username: String, password: String) {
        TODO("Not yet implemented")
    }
}