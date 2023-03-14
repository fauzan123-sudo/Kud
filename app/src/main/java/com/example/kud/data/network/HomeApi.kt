package com.example.kud.data.network

import com.example.kud.data.model.*
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET

interface HomeApi {

    @GET("")
    suspend fun getData(
        @Body body: RequestData
    ): Response<DataRequest>

    @GET("kategori-obat")
    suspend fun getCategory(): Response<Category>

    @GET("obat")
    suspend fun getDataDrug() : Response<DataDrug>
}