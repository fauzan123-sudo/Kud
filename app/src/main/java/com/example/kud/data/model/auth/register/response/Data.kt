package com.example.kud.data.model.auth.register.response

data class Data(
    val access_token: String,
    val token_type: String,
    val user: User
)