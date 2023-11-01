package com.example.taskcdp.util

import android.content.Context
import android.content.SharedPreferences

class SessionManager(context: Context) {
    private val sharedPreferences: SharedPreferences =
        context.getSharedPreferences("AppSharedPref", Context.MODE_PRIVATE)

    companion object {
        const val KEY_USERNAME = "username"
        const val KEY_PASSWORD = "password"
        const val KEY_REMEMBER_USER = "isRememberUser"
        const val KEY_TOKEN = "token"
        const val KEY_IS_AUTH = "user_authenticated"
    }

    fun saveLoginDetails(username: String, password: String) {
        sharedPreferences.edit {
            putString(KEY_USERNAME, username)
            putString(KEY_PASSWORD, password)
        }
    }

    fun getLoginDetails(): Pair<String, String>? {
        val username = sharedPreferences.getString(KEY_USERNAME, null)
        val password = sharedPreferences.getString(KEY_PASSWORD, null)
        return if (username != null && password != null) {
            Pair(username, password)
        } else {
            null
        }
    }

    fun rememberUser(): Boolean {
        return sharedPreferences.getBoolean(KEY_REMEMBER_USER, false)
    }

    fun rememberUser(isRememberUser: Boolean) {
        sharedPreferences.edit {
            putBoolean(KEY_REMEMBER_USER, isRememberUser)
        }
    }

    fun isAuth(): Boolean {
        return sharedPreferences.getBoolean(KEY_IS_AUTH, false)
    }

    fun isAuth(isRememberUser: Boolean) {
        sharedPreferences.edit {
            putBoolean(KEY_IS_AUTH, isRememberUser)
        }
    }

    fun clearData() {
        sharedPreferences.edit().clear().apply()
    }

    fun clearLoginDetails() {
        sharedPreferences.edit {
            remove(KEY_USERNAME)
            remove(KEY_PASSWORD)
        }
    }
}

// Extension function for SharedPreferences.Editor
inline fun SharedPreferences.edit(
    commit: Boolean = false,
    action: SharedPreferences.Editor.() -> Unit
) {
    val editor = edit()
    action(editor)
    if (commit) {
        editor.commit()
    } else {
        editor.apply()
    }
}