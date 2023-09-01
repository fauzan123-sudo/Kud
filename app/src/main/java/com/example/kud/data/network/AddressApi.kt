package com.example.kud.data.network

import com.example.kud.data.model.*
import com.example.kud.data.model.address.list.UserAddressModel
import com.example.kud.data.model.address.request.RequestAddress
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

interface AddressApi {

    @POST("alamat-list")
    suspend fun userAddress(
       @Body request: RequestAddress
    ): Response<UserAddressModel>
}