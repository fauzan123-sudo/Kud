package com.example.kud.data.model.cart.list

data class DataX(
    val id_keranjang: Int,
    val id_obat: Int,
    val image: String,
    val kategori: String,
    val nama_obat: String,
    val qty: String,
    val harga: String,
    val total:String,
    var isSelected:Boolean = false
)