package com.example.kud.data.network

import com.example.kud.data.model.*
import retrofit2.Response
import retrofit2.http.GET

interface ProfileApi {

    @GET("profile")
    suspend fun getProfile(
    ): Response<ProfileResponse>
}