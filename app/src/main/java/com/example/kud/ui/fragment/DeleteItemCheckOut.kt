package com.example.kud.ui.fragment

interface DeleteItemCheckOut {
    fun deleteItemCheckOut(position: Int, data: CheckOut)
    fun upsert(data: CheckOut)
    fun grandTotal(total:Int)
    fun grandTotalItem(total:Int)
}