package com.example.kud.data.network

import com.example.kud.data.model.*
import com.example.kud.data.model.transaction.request.RequestAddPayment
import com.example.kud.data.model.transaction.response.AddCartResponse
import com.example.kud.data.model.transaction.response.CartRequest
import com.example.kud.data.model.transaction.response.addPayment.AddPaymentResponse
import com.example.kud.data.model.transaction.response.detail.DetailTransactionModel
import com.example.kud.data.model.transaction.response.history.HistoryTransactionModel
import com.example.kud.data.model.upload.ImageUploadResponse
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Response
import retrofit2.http.*

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

    @Multipart
    @POST("upload-bukti-transfer")
    suspend fun imageUpload(
        @Part("kode_transaksi") transactionCode: RequestBody,
        @Part bukti_transfer: MultipartBody.Part
    ): Response<ImageUploadResponse>



}