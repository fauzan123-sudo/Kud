package com.example.kud.data.network

import com.example.kud.data.model.auth.login.LoginRequest
import com.example.kud.data.model.auth.login.LoginResponse
import com.example.kud.data.model.auth.logout.LogOutResponse
import com.example.kud.data.model.auth.register.response.RegisterResponse
import retrofit2.Response
import retrofit2.http.*

interface AuthApi {

    @POST("login")
    suspend fun loginUser(
        @Body loginRequest: LoginRequest
    ): Response<LoginResponse>

    @FormUrlEncoded
    @POST("register")
    suspend fun registerUser(
//        @Body request: RegisterRequest
        @Field("name") name: String,
        @Field("email") email: String,
        @Field("password") password: String,
        @Field("alamat") alamat: String,
        ): Response<RegisterResponse>

    @GET("logout")
    suspend fun logout(
    ): Response<LogOutResponse>

}