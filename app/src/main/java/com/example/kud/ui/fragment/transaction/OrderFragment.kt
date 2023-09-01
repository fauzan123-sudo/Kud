package com.example.kud.ui.fragment.transaction

import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.example.kud.data.adapter.AdapterCart
import com.example.kud.databinding.FragmentOrderBinding
import com.example.kud.ui.base.BaseFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class OrderFragment : BaseFragment<FragmentOrderBinding>(FragmentOrderBinding::inflate){

//    lateinit var recyclerView: RecyclerView
//    lateinit var adapterCart: AdapterCart

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

//        initRecyclerView()
//
//        loadListData()

    }

    private fun initRecyclerView() {

    }

}