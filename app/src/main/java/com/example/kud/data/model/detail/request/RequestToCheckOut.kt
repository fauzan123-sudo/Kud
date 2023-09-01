package com.example.kud.data.model.detail.request

data class RequestToCheckOut(
    val id_pelanggan: String,
    val id_obat: String,
    val qty: String,
)
