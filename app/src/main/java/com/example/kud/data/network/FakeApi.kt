package com.example.kud.data.network

import retrofit2.Response
import retrofit2.http.GET

interface FakeApi {
    @GET("posts")
    fun getPosts(): List<com.example.kud.data.model.FakeApiItem>



}