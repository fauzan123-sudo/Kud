package com.example.kud.ui.fragment

import com.example.kud.data.model.CheckOut
import com.example.kud.data.model.detail.DetailProduct

interface DeleteItemCheckOut {
    fun deleteItemCheckOut(position: Int, data: CheckOut)
    fun upsert(data: CheckOut)
    fun grandTotal(total:Int)
    fun grandTotalItem(total:Int)
}