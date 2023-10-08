package com.example.kud.data.model.address.request

data class RequestAddOrEdit(
    val id_kustomer: String,
    val judul: String,
    val alamat: String,
    val default:Int? = 0
)
