package com.example.kud.data.model.transaction.request

data class RequestAddPayment(
    val id_keranjang:String,
    val id_pelanggan:String,
    val id_alamat:String,
    val metode_kirim:String,
    val metode_bayar:String
)
