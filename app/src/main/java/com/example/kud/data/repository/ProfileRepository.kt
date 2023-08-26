package com.example.kud.data.repository

import com.example.kud.data.network.ProfileApi
import javax.inject.Inject


class ProfileRepository @Inject constructor(private val api: ProfileApi) : BaseRepo() {

    suspend fun profileUser() = safeApiCall { api.getProfile() }
}