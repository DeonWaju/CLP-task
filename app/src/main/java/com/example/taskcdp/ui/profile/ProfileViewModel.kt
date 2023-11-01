package com.example.taskcdp.ui.profile

import androidx.lifecycle.ViewModel
import com.example.taskcdp.data.AuthRepository
import com.example.taskcdp.data.model.Responses
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

data class LoginResponse(
    var data: Responses.LoginUserDataResponse,
    val loading: Boolean = false,
    val error: String = "",
)

@HiltViewModel
class ProfileViewModel @Inject constructor(
    private val authRepository: AuthRepository,
) : ViewModel() {


}