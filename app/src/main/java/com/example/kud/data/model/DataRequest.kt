package com.example.kud.data.model

data class DataRequest(
    val `data`: List<DataX>,
    val msg: String,
    val status: Int
)