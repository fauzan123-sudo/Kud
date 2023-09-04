package com.example.kud.data.network

import com.example.kud.data.model.*
import com.example.kud.data.model.allProduct.response.CategoryResponse
import com.example.kud.data.model.home.list.ListDrugModel
import com.example.kud.data.model.product.DetailProductResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface HomeApi {

    @GET("kategori-obat")
    suspend fun getCategory(): Response<CategoryResponse>

    @GET("obat")
    suspend fun getDataDrug(): Response<ListDrugModel>

    @GET("search-obat")
    suspend fun searchDrug(
        @Query("q") q: String? = null,
        @Query("k") k: String? = null,
    ): Response<ListDrugModel>

    @GET("detail-obat")
    suspend fun getDetailObat(@Query("id_obat") drugId: Int): Response<DetailProductResponse>
}