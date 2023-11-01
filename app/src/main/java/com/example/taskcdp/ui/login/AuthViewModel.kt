package com.example.taskcdp.ui.login

import Resource
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.taskcdp.data.local.entity.UserProfile
import com.example.taskcdp.domain.usecases.AuthRepository
import com.example.taskcdp.data.model.LoginRequest
import com.example.taskcdp.data.model.Responses
import com.example.taskcdp.domain.usecases.SaveUserProfileRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

data class LoginResponse(
    var data: Responses.LoginUserDataResponse,
    val loading: Boolean = false,
    val error: String = "",
)

@HiltViewModel
class AuthViewModel @Inject constructor(
    private val authRepository: AuthRepository,
    private val saveUserProfileRepository: SaveUserProfileRepository,
) : ViewModel() {

    private val _rememberMeChecked = MutableLiveData<Boolean>()
    val rememberMeChecked: LiveData<Boolean> get() = _rememberMeChecked

    private val _loginState = MutableStateFlow(LoginResponse(Responses.LoginUserDataResponse()))
    var loginState: StateFlow<LoginResponse> = _loginState.asStateFlow()

    fun login(loginRequest: LoginRequest) {
        _loginState.update { it.copy(loading = true) }

        viewModelScope.launch {
            authRepository.loginUser(loginRequest).collect { result ->
                when (result) {
                    is Resource.Success -> {
                        _loginState.update {
                            it.copy(
                                loading = false,
                                data = result.data!!
                            )
                        }
                    }

                    is Resource.Error -> {
                        _loginState.update {
                            it.copy(
                                loading = false,
                                data = result.data ?: Responses.LoginUserDataResponse(),
                                error = result.message ?: "Something went wrong"
                            )
                        }
                    }

                    is Resource.Loading -> {
                        _loginState.update { it.copy(loading = true) }
                    }

                    else -> {}
                }
            }
        }
    }

    fun saveUserDetails(response: Responses.LoginUserDataResponse){
        viewModelScope.launch {
            saveUserProfileRepository.invoke(
                UserProfile(
                    id = response.id.toInt(),
                    image = response.image,
                    firstName = response.firstName,
                    lastName = response.lastName,
                    email = response.email,
                )
            )
        }
    }

    fun onRememberMeChecked(checked: Boolean) {
        _rememberMeChecked.value = checked
    }
    // A placeholder username validation check

    fun getLoginDetails(): Pair<String, String>? = authRepository.getLoginDetails()

    fun saveLoginDetails(username: String, password: String) =
        authRepository.saveLoginDetails(username, password)

    fun saveUserIsAuthenticated(isAuth: Boolean) =
        authRepository.saveUserIsAuthenticated(isAuth)

    fun userIsAuthenticated(): Boolean =
        authRepository.userIsAuthenticated()


    fun getIsRememberButtonCheck() = authRepository.getRememberUser()

    fun saveIsRememberButtonCheck(isRememberButtonClicked: Boolean) =
        authRepository.saveRememberUser(isRememberButtonClicked)

    fun clearLoginDetails() = authRepository.clearLoginDetails()

    fun clearAllData() = authRepository.clearAllData()

    fun isUserNameValid(username: String): Boolean {
        return username.length > 3
    }

    fun isPasswordValid(password: String): Boolean {
        return password.length > 5
    }
}