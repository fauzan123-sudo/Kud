package com.example.kud.data.model.address.list

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Data(
    val alamat: String? = null,
    val created_at: String? = null,
    val id: Int,
    val id_kustomer: String,
    val nama: String,
    val updated_at: String? = null,
    val status: Boolean = false
) : Parcelable