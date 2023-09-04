package com.example.kud.ui.fragment.home

import android.os.Bundle
import android.view.View
import androidx.appcompat.widget.SearchView
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.kud.data.adapter.AdapterCategory
import com.example.kud.data.adapter.AdapterData
import com.example.kud.data.model.allProduct.response.Data
import com.example.kud.databinding.FragmentAllProductBinding
import com.example.kud.ui.base.BaseFragment
import com.example.kud.ui.fragment.RecyclerViewClickListener
import com.example.kud.ui.viewModel.HomeViewModel
import com.example.kud.utils.NetworkResult
import com.example.kud.utils.handleApiError
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class AllProductFragment :
    BaseFragment<FragmentAllProductBinding>(FragmentAllProductBinding::inflate),
    AdapterCategory.ItemClickListener, RecyclerViewClickListener {

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
        toolbarInit()

    }

    private fun searchDrug(query: String?, key: String) {
        viewModel.requestSearchDrug(query!!, key)
        viewModel.searchDrug.observe(viewLifecycleOwner) {
            binding.progressBar.isVisible = false
            when (it) {
                is NetworkResult.Success -> {
                    val response = it.data!!
                    val data = response.data
                    adapterData.differ.submitList(data)
                }

                is NetworkResult.Loading -> {
                    binding.progressBar.isVisible = true
                }

                is NetworkResult.Error -> {
                    handleApiError(it.message)
                }
            }
        }
    }

    private fun toolbarInit() {
        binding.toolbarItems.searchView.setOnQueryTextListener(object :
            SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                searchDrug(query, "")
                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                // Do something when user changes text
                return true
            }
        })

        binding.toolbarItems.searchView.setOnQueryTextFocusChangeListener { _, hasFocus ->
            if (hasFocus) {
//                Toast.makeText(requireContext(), "sedang aktif", Toast.LENGTH_SHORT).show()
            } else {
//                Toast.makeText(requireContext(), "tidak aktif", Toast.LENGTH_SHORT).show()
            }
        }

//        binding.toolbarItems.menu.setOnClickListener {
//            Toast.makeText(requireContext(), "menu", Toast.LENGTH_SHORT).show()
//        }
    }

    private fun loadDataCategory() {
        viewModel.category()
        viewModel.getCategory.observe(viewLifecycleOwner) {
            when (it) {
                is NetworkResult.Success -> {
                    val response = it.data!!
                    val data = response.data.toMutableList()
                    val newDrug = Data("","Semua")
                    data.add(0, newDrug)
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
        adapterData.listener = this
        recyclerView = binding.recAllProduct
        recyclerView.adapter = adapterData
        recyclerView.layoutManager = GridLayoutManager(requireContext(), 2)
    }

    private fun setRecycleCategory() {
        adapterCategorise = AdapterCategory()
        adapterCategorise.itemClickListener = this
        recyclerView = binding.recFilter
        recyclerView.adapter = adapterCategorise
        recyclerView.layoutManager =
            LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
    }

    override fun getCategory(drugId: String) {
        searchDrug("", drugId)
    }

    override fun onItemClicked(view: View, data: com.example.kud.data.model.home.list.Data) {
        val dataName = data.nama
        val dataImage = data.foto
        val dataPrice = data.harga
        val dataDescription = data.deskripsi
        val stockDetail = data.stok.toInt()
        val idCategory = data.id_kategori
        val idDrug = data.id_obat
        val sendData = com.example.kud.data.model.home.list.Data(
            dataDescription,
            idDrug,
            dataImage,
            dataPrice,
            idCategory,
            dataName,
            stockDetail.toString(),
        )
        val action = AllProductFragmentDirections.actionSemuaProdukFragmentToDetailFragment(sendData)
        findNavController().navigate(action)
    }

}