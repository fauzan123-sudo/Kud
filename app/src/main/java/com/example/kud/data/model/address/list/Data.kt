package com.example.kud.data.model.address.list

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Data(
    val id: Int,
    val id_kustomer: String,
    val nama: String,
    var default: String? = null,
    val alamat: String? = null,
    val created_at: String? = null,
    val updated_at: String? = null,
    val status: Boolean = false,
    var isChecked: Boolean
) : Parcelable