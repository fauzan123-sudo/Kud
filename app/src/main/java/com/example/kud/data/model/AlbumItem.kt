package com.example.kud.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity(tableName = "KUD")
data class AlbumItem(
    @PrimaryKey(autoGenerate = true)
    val UID:Int = 0,
    val albumId: Int,
    val id: Int,
    val thumbnailUrl: String,
    val title: String,
    val url: String
)