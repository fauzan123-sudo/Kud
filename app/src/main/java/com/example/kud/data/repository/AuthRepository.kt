package com.example.kud.data.repository

import com.example.kud.data.model.auth.login.LoginRequest
import com.example.kud.data.network.AuthApi
import javax.inject.Inject


class AuthRepository @Inject constructor(private val api: AuthApi) : BaseRepo() {

    suspend fun loginUser(loginRequest: LoginRequest) = safeApiCall { api.loginUser(loginRequest) }
    suspend fun registerUser(
        name: String,
        email: String,
        password: String,
        alamat: String,
    ) = safeApiCall { api.registerUser(name, email, password, alamat) }

    suspend fun logOut() = safeApiCall { api.logout() }
}