package com.example.kud.ui.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.kud.R
import com.example.kud.data.adapter.AdapterData
import com.example.kud.data.adapter.AdapterKategori
import com.example.kud.databinding.FragmentSemuaProdukBinding
import com.example.kud.ui.base.BaseFragment
import com.example.kud.ui.viewModel.HomeViewModel
import com.example.kud.utils.NetworkResult
import com.example.kud.utils.handleApiError
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class SemuaProdukFragment :
    BaseFragment<FragmentSemuaProdukBinding>(FragmentSemuaProdukBinding::inflate) {

    private val viewModel: HomeViewModel by viewModels()
    private lateinit var recyclerviewItems: RecyclerView
    private lateinit var adapterData: AdapterData

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        adapterData = AdapterData(requireContext())
        recyclerviewItems = binding.recAllProduct
        recyclerviewItems.adapter = adapterData
        recyclerviewItems.layoutManager = GridLayoutManager(requireContext(), 2)

        viewModel.drug()
        viewModel.getDrug.observe(viewLifecycleOwner) {
            when (it) {
                is NetworkResult.Success -> {
                    val statusResponse = it.data!!.status
                    if (statusResponse == 200) {
                        adapterData.differ.submitList(it.data.data)
                    } else {
                        handleApiError(statusResponse.toString())
                    }
                }

                is NetworkResult.Error -> {
                    handleApiError(it.message)
                }
            }
        }

    }

}