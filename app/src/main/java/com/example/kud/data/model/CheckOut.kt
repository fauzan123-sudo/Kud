package com.example.kud.data.model

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.android.parcel.Parcelize

@Parcelize
@Entity(tableName = "checkOut")
data class CheckOut(
    @PrimaryKey(autoGenerate = true)
    val id:Int = 0,
    val imageItem: String,
    val nameItem:String?,
    val category:String?,
    val priceItem:Int?,
    var amountItem:Int,
    val stockItem:Int?
): Parcelable
