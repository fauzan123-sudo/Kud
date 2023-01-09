package com.example.kud.data.repository

import com.example.kud.data.network.FakeApi
import com.example.kud.data.network.RetrofitInstance
import kotlinx.coroutines.CoroutineDispatcher
import javax.inject.Inject

class RepositorySafe(
    private val apiCaller: SafeApiCaller
) {

    suspend fun getPosts() =
        apiCaller.safeApiCall{
            RetrofitInstance.api.getPosts()
        }
}