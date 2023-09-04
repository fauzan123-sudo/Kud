package com.example.kud.data.model.transaction.response.history

data class HistoryTransactionModel(
    val `data`: List<Data>,
    val msg: String,
    val status: Int
)