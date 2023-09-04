package com.example.kud.data.network

import com.example.kud.data.model.*
import com.example.kud.data.model.transaction.request.RequestAddPayment
import com.example.kud.data.model.transaction.response.AddCartResponse
import com.example.kud.data.model.transaction.response.CartRequest
import com.example.kud.data.model.transaction.response.addPayment.AddPaymentResponse
import com.example.kud.data.model.transaction.response.detail.DetailTransactionModel
import com.example.kud.data.model.transaction.response.history.HistoryTransactionModel
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query

interface TransactionApi {

    @POST("keranjang/store")
    suspend fun getCart(
        @Body request: CartRequest
    ): Response<AddCartResponse>

    @GET("list-transaksi")
    suspend fun getHistoryTransaction(
        @Query("id_pelanggan") userId: Int
    ): Response<HistoryTransactionModel>

    @GET("detail-transaksi")
    suspend fun getDetailTransaction(
        @Query("kode_transaksi") transactionCode: String
    ): Response<DetailTransactionModel>

    @POST("tambah-transaksi")
    suspend fun getAddTransaction(
        @Body request: RequestAddPayment
    ): Response<AddPaymentResponse>



}