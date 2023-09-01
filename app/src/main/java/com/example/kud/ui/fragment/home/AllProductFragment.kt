package com.example.kud.ui.fragment.home

import android.os.Bundle
import android.view.View
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.kud.data.adapter.AdapterData
import com.example.kud.databinding.FragmentAllProductBinding
import com.example.kud.ui.base.BaseFragment
import com.example.kud.ui.viewModel.HomeViewModel
import com.example.kud.utils.NetworkResult
import com.example.kud.utils.handleApiError
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class AllProductFragment :
    BaseFragment<FragmentAllProductBinding>(FragmentAllProductBinding::inflate) {

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
            binding.progressBar.isVisible = false
            when (it) {
                is NetworkResult.Success -> {
                    val statusResponse = it.data!!.status
                    if (statusResponse == 200) {
                        adapterData.differ.submitList(it.data.data)
                    } else {
                        handleApiError(statusResponse.toString())
                    }
                }
                is NetworkResult.Loading ->  binding.progressBar.isVisible = true

                is NetworkResult.Error -> {
                    handleApiError(it.message)
                }
            }
        }

    }

}