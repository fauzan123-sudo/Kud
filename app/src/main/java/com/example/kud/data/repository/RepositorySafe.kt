package com.example.kud.data.repository

class RepositorySafe(
    private val apiCaller: SafeApiCaller
) {

//    suspend fun getPosts() =
//        apiCaller.safeApiCall{
//            RetrofitInstance.api.getPosts()
//        }
}