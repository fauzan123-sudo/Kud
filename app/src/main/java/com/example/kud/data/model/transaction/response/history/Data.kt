package com.example.kud.data.model.transaction.response.history

data class Data(
    val alamat_tujuan: String,
    val bukti_transfer: Any,
    val created_at: String,
    val created_by: Any,
    val id_pelanggan: String,
    val id_transaksi: Int,
    val jumlah_barang: String,
    val kode_transaksi: String,
    val metode_bayar: String,
    val metode_kirim: String,
    val no_pesan: String,
    val ongkir: String,
    val total_harga: String,
    val updated_at: String,
    val updated_by: Any
)