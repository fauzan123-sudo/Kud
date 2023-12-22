package com.example.kud.data.model.transaction.detail

data class Data(
    val alamat_pengiriman: String,
    val order_id: String,
    val produk: String,
    val status: String,
    val status_code: String,
    val tanggal: String,
    val total_harga: String
)