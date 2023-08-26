package com.example.kud.data.network

import com.example.kud.data.model.*
import com.example.kud.data.model.transaction.AddCartResponse
import com.example.kud.data.model.transaction.CartRequest
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

interface TransactionApi {

    @POST("keranjang/store")
    suspend fun getCart(
        @Body request: CartRequest
    ): Response<AddCartResponse>
}