package com.example.kud.data.repository

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class ErrorResponse(
    val errors: List<String>
)