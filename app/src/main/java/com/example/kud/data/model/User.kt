package com.example.kud.data.model

import android.graphics.Bitmap
import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize

@Parcelize
@Entity(tableName = "users")
data class User(
    @PrimaryKey(autoGenerate = true)
    val id:Int = 0,
    val firstName:String,
    val middleName:String,
    val lastName:String,
    val image:Bitmap?
):Parcelable
