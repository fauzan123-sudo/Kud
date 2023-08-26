package com.example.kud.data.repository

import com.example.kud.data.model.RequestData
import com.example.kud.data.network.HomeApi
import javax.inject.Inject

class HomeRepository @Inject constructor(private val api: HomeApi) : BaseRepo() {

    suspend fun home(body: RequestData) = safeApiCall { api.getData(body) }
    suspend fun category() = safeApiCall { api.getCategory() }
    suspend fun drug() = safeApiCall { api.getDataDrug() }
    suspend fun detailDrug(id_drug: Int) = safeApiCall { api.getDetailObat(id_drug) }
}