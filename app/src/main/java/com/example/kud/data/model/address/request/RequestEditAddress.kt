package com.example.kud.data.model.address.request

data class RequestEditAddress(
    val id_alamat:String,
    val judul:String,
    val alamat:String,
    val default:Int?
)
