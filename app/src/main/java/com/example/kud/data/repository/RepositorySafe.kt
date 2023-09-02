package com.example.kud.data.repository

import com.example.kud.data.network.RetrofitInstance

class RepositorySafe(
    private val apiCaller: SafeApiCaller
) {

    suspend fun getPosts() =
        apiCaller.safeApiCall{
            RetrofitInstance.api.getPosts()
        }
}