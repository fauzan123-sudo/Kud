package com.example.kud.data.model.transaction.response.history

data class Data(
    val order_id: String,
    val status: String,
    val status_code: String,
    val tanggal: String,
    val produk: String,
    val total_harga: String,
    val alamat_pengiriman: String

)