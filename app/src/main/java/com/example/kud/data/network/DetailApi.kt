package com.example.kud.data.network

import com.example.kud.data.model.*
import com.example.kud.data.model.cart.list.ListCartModel
import com.example.kud.data.model.detail.addCheckOut.AddToCheckOutModel
import com.example.kud.data.model.detail.request.RequestToCart
import com.example.kud.data.model.detail.request.RequestToCheckOut
import com.example.kud.data.model.product.DetailProductResponse
import com.example.kud.data.model.transaction.response.AddCartResponse
import retrofit2.Response
import retrofit2.http.*

interface DetailApi {
    @POST("keranjang/store-one")
    suspend fun addToCheckOut(
        @Body request: RequestToCheckOut
    ): Response<AddToCheckOutModel>

    @GET("detail-obat")
    suspend fun getDetailDrug(@Query("id_obat") drugId: Int): Response<DetailProductResponse>

    @POST("keranjang/store")
    suspend fun addToCart(
        @Body request: RequestToCart
    ): Response<AddCartResponse>

    @POST("keranjang/list")
    suspend fun listOfCart(
        @Field("id_pelanggan") userId: String
    ): Response<ListCartModel>
}