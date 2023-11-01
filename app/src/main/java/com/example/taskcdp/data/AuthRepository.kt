package com.example.taskcdp.data

interface AuthRepository {
    fun getLoginDetails(): Pair<String, String>?
    fun saveLoginDetails(username: String, password: String)
    fun getRememberUser(): Boolean
    fun saveRememberUser(isChecked: Boolean)
    fun clearLoginDetails()
    fun clearAllData()
    suspend fun login(username: String, password: String)
}