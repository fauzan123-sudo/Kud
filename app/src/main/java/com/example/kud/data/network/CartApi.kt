package com.example.kud.data.network

import com.example.kud.data.model.*
import com.example.kud.data.model.cart.checkUncheck.CheckUnCheckResponse
import com.example.kud.data.model.cart.delete.DeleteDrugResponse
import com.example.kud.data.model.cart.list.ListCartModel
import com.example.kud.data.model.cart.plusMinus.PlusMinusModel
import com.example.kud.data.model.cart.request.RequestCheckUnCheck
import com.example.kud.data.model.cart.request.RequestDeleteDrug
import com.example.kud.data.model.cart.request.RequestListCart
import com.example.kud.data.model.cart.request.RequestPlusMinus
//import com.example.kud.data.model.checkOut.plusMinus.PlusMinusModel
//import com.example.kud.data.model.checkOut.request.RequestPlusMinus
import com.example.kud.data.model.transaction.AddCartResponse
import com.example.kud.data.model.transaction.CartRequest
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.Field
import retrofit2.http.POST

interface CartApi {
    @POST("keranjang/store")
    suspend fun addCart(
        @Body request: CartRequest
    ): Response<AddCartResponse>

    @POST("keranjang/list")
    suspend fun listOfCart(
        @Body request: RequestListCart
    ): Response<ListCartModel>

    @POST("keranjang/tambah-kurang")
    suspend fun plusMinus(
        @Body request: RequestPlusMinus
    ): Response<PlusMinusModel>

    @POST("keranjang/delete")
    suspend fun deleteDrug(
        @Body request: RequestDeleteDrug
    ): Response<DeleteDrugResponse>

    @POST("keranjang/check-uncheck")
    suspend fun checkUnCheck(
        @Body request: RequestCheckUnCheck
    ): Response<CheckUnCheckResponse>


}