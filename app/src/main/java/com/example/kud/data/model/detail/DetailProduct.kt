package com.example.kud.data.model.detail

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class DetailProduct(
    val deskripsi: String?,
    val foto: String?,
    val harga: String?,
    val total: String,
    val id_kategori: String?,
    val jumlah: Int?,
    val id_obat: Int?,
    val nama: String?,
    val stok: Int?,
    val status: Int,
    val id_keranjang: Int
) : Parcelable
