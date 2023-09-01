package com.example.kud.ui.fragment

import com.example.kud.data.model.CheckOut

interface CheckOutHelper {
    fun deleteItemCheckOut(position: Int, data: CheckOut)
    fun upsert(drugId: Int?, status: Int)
//    fun grandTotal(total:Int)
//    fun grandTotalItem(total:Int)
}