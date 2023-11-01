package com.example.taskcdp.ui.login

import androidx.lifecycle.ViewModel
import android.util.Patterns
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.taskcdp.R
import com.example.taskcdp.data.AuthRepository
import com.example.taskcdp.ui.login.ui.login.LoginFormState

import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class AuthViewModel @Inject constructor(
    private val authRepository: AuthRepository,
) : ViewModel() {

    private val _loginForm = MutableLiveData<LoginFormState>()
    val loginFormState: LiveData<LoginFormState> = _loginForm

    private val _rememberMeChecked = MutableLiveData<Boolean>()
    val rememberMeChecked: LiveData<Boolean> get() = _rememberMeChecked

    fun onRememberMeChecked(checked: Boolean) {
        _rememberMeChecked.value = checked
    }
    // A placeholder username validation check

    fun getLoginDetails(): Pair<String, String>? = authRepository.getLoginDetails()

    fun saveLoginDetails(username: String, password: String) =
        authRepository.saveLoginDetails(username, password)


    fun getIsRememberButtonCheck() = authRepository.getRememberUser()

    fun saveIsRememberButtonCheck(isRememberButtonClicked: Boolean) =
        authRepository.saveRememberUser(isRememberButtonClicked)

    fun clearLoginDetails() = authRepository.clearLoginDetails()

    fun clearAllData() = authRepository.clearAllData()

    fun loginDataChanged(username: String, password: String) {
        if (!isUserNameValid(username)) {
            _loginForm.value = LoginFormState(usernameError = R.string.invalid_username)
        } else if (!isPasswordValid(password)) {
            _loginForm.value = LoginFormState(passwordError = R.string.invalid_password)
        } else {
            _loginForm.value = LoginFormState(isDataValid = true)
        }
    }

    private fun isUserNameValid(username: String): Boolean {
        return if (username.contains('@')) {
            Patterns.EMAIL_ADDRESS.matcher(username).matches()
        } else {
            username.isNotBlank()
        }
    }

    private fun isPasswordValid(password: String): Boolean {
        return password.length > 5
    }
}