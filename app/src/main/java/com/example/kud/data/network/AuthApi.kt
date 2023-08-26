package com.example.kud.data.network

import com.example.kud.data.model.LoginRequest
import com.example.kud.data.model.LoginResponse
import com.example.kud.data.model.ProfileResponse
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

}