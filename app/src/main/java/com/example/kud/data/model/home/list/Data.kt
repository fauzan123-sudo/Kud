package com.example.kud.data.model.home.list

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Data(
    val deskripsi: String,
    val id_obat:Int,
    val foto: String,
    val harga: String,
    val id_kategori: String,
    val nama: String,
    val stok: String
) : Parcelable