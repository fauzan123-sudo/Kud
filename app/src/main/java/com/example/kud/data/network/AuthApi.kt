package com.example.kud.data.network

import com.example.kud.data.model.ProfileResponse
import com.example.kud.data.model.auth.login.LoginRequest
import com.example.kud.data.model.auth.login.LoginResponse
import com.example.kud.data.model.auth.logout.LogOutResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

interface AuthApi {

    @POST("login")
    suspend fun loginUser(
        @Body loginRequest: LoginRequest
    ): Response<LoginResponse>

    @GET("profile")
    suspend fun profileUser(
    ): Response<ProfileResponse>

    @GET("logout")
    suspend fun logout(
    ): Response<LogOutResponse>

}