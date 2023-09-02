package com.example.kud.ui.fragment.home

import android.os.Bundle
import android.view.View
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.kud.data.adapter.AdapterCategory
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
    private lateinit var recyclerView: RecyclerView
    private lateinit var adapterData: AdapterData
    private lateinit var adapterCategorise: AdapterCategory

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setRecycleCategory()
        setRecycleProduct()
        loadDataDrug()
        loadDataCategory()

    }

    private fun loadDataCategory() {
        viewModel.category()
        viewModel.getCategory.observe(viewLifecycleOwner) {
            when (it) {
                is NetworkResult.Success -> {
                    val response = it.data!!
                    val data = response.data
                    adapterCategorise.differ.submitList(data)
                }

                is NetworkResult.Loading -> {

                }

                is NetworkResult.Error -> {
                    handleApiError(it.message)
                }
            }
        }
    }

    private fun loadDataDrug() {
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
                is NetworkResult.Loading -> binding.progressBar.isVisible = true

                is NetworkResult.Error -> {
                    handleApiError(it.message)
                }
            }
        }
    }

    private fun setRecycleProduct() {
        adapterData = AdapterData(requireContext())
        recyclerView = binding.recAllProduct
        recyclerView.adapter = adapterData
        recyclerView.layoutManager = GridLayoutManager(requireContext(), 2)
    }

    private fun setRecycleCategory() {
        adapterCategorise = AdapterCategory()
//        adapterData.listener = this
        recyclerView = binding.recFilter
        recyclerView.adapter = adapterCategorise
        recyclerView.layoutManager =
            LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
    }

}