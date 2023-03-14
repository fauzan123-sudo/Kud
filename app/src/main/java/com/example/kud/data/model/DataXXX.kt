package com.example.kud.data.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class DataXXX(
    val created_at: String?,
    val created_by: Int?,
    val deskripsi: String?,
    val foto: String?,
    val harga: String?,
    val id_kategori: Int?,
    val id_obat: Int?,
    val nama: String?,
    val stok: Int?,
    val updated_at: String?,
    val updated_by: Int?
) : Parcelable