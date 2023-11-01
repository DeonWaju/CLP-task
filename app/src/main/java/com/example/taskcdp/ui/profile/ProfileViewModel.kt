package com.example.taskcdp.ui.profile

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.taskcdp.data.local.entity.UserProfile
import com.example.taskcdp.domain.usecases.AuthRepository
import com.example.taskcdp.data.model.Responses
import com.example.taskcdp.domain.usecases.SaveUserProfileRepository
import com.example.taskcdp.domain.usecases.UpdateProfileImageRepository
import com.example.taskcdp.domain.usecases.UserProfileRepository
import com.example.taskcdp.ui.login.LoginResponse
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject


data class UserProfileData(
    val profile: UserProfile
)

@HiltViewModel
class ProfileViewModel @Inject constructor(
    private val userProfileRepository: UserProfileRepository,
    private val updateProfileImageRepository: UpdateProfileImageRepository
) : ViewModel() {

    init {
        userProfile()
    }

    private val _profileUserState =
        MutableStateFlow(UserProfileData(UserProfile(-1, "", "", "", "")))
    var profileUserState: StateFlow<UserProfileData?> = _profileUserState.asStateFlow()

    private fun userProfile() {
        viewModelScope.launch {
            userProfileRepository.invoke().collect { profile ->
                _profileUserState.update { currentState ->
                    UserProfileData(profile!!)
                }
            }
        }
    }

    fun updateProfileImage(id: Int, imageUrl: String) {
        viewModelScope.launch {
            updateProfileImageRepository.invoke(id, imageUrl)
        }
    }
}