package com.example.kud.ui.fragment

import android.view.View
import com.example.kud.data.model.home.list.Data

interface RecyclerViewClickListener {
    fun onItemClicked(view: View, data: Data)

}