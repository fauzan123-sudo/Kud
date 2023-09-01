package com.example.kud.data.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
//import kotlinx.android.parcel.Parcelize

@Parcelize
data class DataXXX(
    val deskripsi: String?,
    val foto: String?,
    val harga: String?,
    val id_kategori: String?,
    val id_obat: Int?,
    val nama: String?,
    val stok: Int?,
) : Parcelable