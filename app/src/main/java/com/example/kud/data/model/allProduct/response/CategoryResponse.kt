package com.example.kud.data.model.allProduct.response

data class CategoryResponse(
    val `data`: List<Data>,
    val msg: String,
    val status: Int
)