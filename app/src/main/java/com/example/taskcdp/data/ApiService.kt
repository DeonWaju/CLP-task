package com.example.taskcdp.data

import com.example.taskcdp.data.model.LoginRequest
import com.example.taskcdp.data.model.Responses
import retrofit2.http.Body
import retrofit2.http.POST

interface ApiService {
    @POST("/auth/login")
    suspend fun login(
        @Body loginRequest: LoginRequest
    ): Responses.LoginUserDataResponse
}