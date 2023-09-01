package com.example.kud.data.model.checkOut.request

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class RequestList(
    val id_pelanggan: String,
    val tipe: Int,
    val id_keranjang: String
) : Parcelable
