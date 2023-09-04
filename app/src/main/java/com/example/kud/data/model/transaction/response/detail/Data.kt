package com.example.kud.data.model.transaction.response.detail

data class Data(
    val alamat_pengiriman: String,
    val order_id: String,
    val produk: String,
    val status: Any,
    val sub_total: String,
    val tanggal: String
)