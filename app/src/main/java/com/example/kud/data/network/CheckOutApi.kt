package com.example.kud.data.network

import com.example.kud.data.model.*
import com.example.kud.data.model.checkOut.address.UserAddressModel
import com.example.kud.data.model.checkOut.list.ListCheckOutModel
import com.example.kud.data.model.checkOut.plusMinus.PlusMinusModel
import com.example.kud.data.model.checkOut.request.RequestAddress
import com.example.kud.data.model.checkOut.request.RequestPlusMinus
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.Field
import retrofit2.http.POST

interface CheckOutApi {

    @POST("keranjang/list")
    suspend fun getListCheckOut(
        @Field("id_pelanggan") userId:String,
    ): Response<ListCheckOutModel>

    @POST("keranjang/tambah-kurang")
    suspend fun plusMinus(
       @Body request: RequestPlusMinus
    ): Response<PlusMinusModel>

    @POST("alamat-list")
    suspend fun userAddress(
       @Body request: RequestAddress
    ): Response<UserAddressModel>
}