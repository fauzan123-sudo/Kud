package com.example.kud.data.model.product

data class Detail(
    val created_at: String,
    val created_by: String,
    val deskripsi: String,
    val foto: String,
    val harga: String,
    val id_kategori: String,
    val id_obat: Int,
    val nama: String,
    val stok: String,
    val updated_at: String,
    val updated_by: String
)