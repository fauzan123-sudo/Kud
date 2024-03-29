package com.example.kud.data.repository

import com.example.kud.data.model.transaction.request.RequestAddPayment
import com.example.kud.data.network.TransactionApi
import okhttp3.MultipartBody
import okhttp3.RequestBody
import javax.inject.Inject


class TransactionRepository @Inject constructor(private val api: TransactionApi) : BaseRepo() {

    suspend fun getHistoryTransaction(userId: Int) =
        safeApiCall { api.getHistoryTransaction(userId) }

    suspend fun getDetailTransaction(transactionCode: String) =
        safeApiCall { api.getDetailTransaction(transactionCode) }

    suspend fun getAddTransaction(request: RequestAddPayment) =
        safeApiCall { api.getAddTransaction(request) }

    suspend fun imageUpload(transactionCode: RequestBody,
                            file: MultipartBody.Part) =
        safeApiCall { api.imageUpload(transactionCode, file) }
}