package com.example.taskcdp.data

import com.example.taskcdp.data.model.LoginRequest
import com.example.taskcdp.data.model.LoginResponse
import com.example.taskcdp.data.model.UserProfileResponse
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST
import retrofit2.http.Query

interface ApiService {
    @POST("https://dummyjson.com/docs/auth")
    suspend fun login(
        @Body loginRequest: LoginRequest
    ): LoginResponse

    @GET("https://dummyjson.com/docs/user_profile")
    suspend fun getUserProfile(
        @Header("Authorization") token: String
    ): UserProfileResponse
}