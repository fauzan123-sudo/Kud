package com.example.kud.data.repository

import com.example.kud.data.model.transaction.CartRequest
import com.example.kud.data.network.ProfileApi
import com.example.kud.data.network.TransactionApi
import retrofit2.http.Body
import javax.inject.Inject


class TransactionRepository @Inject constructor(private val api: TransactionApi) : BaseRepo() {

    suspend fun getCart(body: CartRequest) = safeApiCall { api.getCart(body) }
}