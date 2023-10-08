package com.example.kud.data.network

import com.example.kud.data.model.*
import com.example.kud.data.model.address.addOrEdit.AddEditAddressResponse
import com.example.kud.data.model.address.list.UserAddressModel
import com.example.kud.data.model.address.request.RequestAddOrEdit
import com.example.kud.data.model.address.request.RequestAddress
import com.example.kud.data.model.address.request.RequestEditAddress
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

interface AddressApi {

    @POST("alamat-list")
    suspend fun userAddress(
       @Body request: RequestAddress
    ): Response<UserAddressModel>

    @POST("alamat-edit")
    suspend fun editAddress(
       @Body request: RequestEditAddress
    ): Response<AddEditAddressResponse>

    @POST("alamat-tambah")
    suspend fun addAddress(
       @Body request: RequestAddOrEdit
    ): Response<AddEditAddressResponse>
}