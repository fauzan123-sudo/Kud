package com.example.kud.ui.fragment

import android.view.View
import com.example.kud.data.model.CheckOut
import com.example.kud.data.model.DataXXX

interface RecyclerViewClickListener {
    fun onItemClicked(view: View, data: DataXXX)

}