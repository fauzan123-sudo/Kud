package com.example.kud.data.network

import com.example.kud.data.model.*
import com.example.kud.data.model.allProduct.response.CategoryResponse
import com.example.kud.data.model.product.DetailProductResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Query

interface HomeApi {

    @GET("")
    suspend fun getData(
        @Body body: RequestData
    ): Response<DataRequest>

    @GET("kategori-obat")
    suspend fun getCategory(): Response<CategoryResponse>

    @GET("obat")
    suspend fun getDataDrug(): Response<DataDrug>

    @GET("detail-obat")
    suspend fun getDetailObat(@Query("id_obat") idObat: Int): Response<DetailProductResponse>
}