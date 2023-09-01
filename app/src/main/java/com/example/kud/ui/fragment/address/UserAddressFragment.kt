package com.example.kud.ui.fragment.address

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.kud.R
import com.example.kud.data.adapter.AdapterAddress
import com.example.kud.data.adapter.NewAdapterCheckOut
import com.example.kud.data.model.address.request.RequestAddress
import com.example.kud.databinding.FragmentUserAddressBinding
import com.example.kud.ui.base.BaseFragment
import com.example.kud.ui.viewModel.AddressViewModel
import com.example.kud.utils.NetworkResult
import com.example.kud.utils.getDataUser
import com.example.kud.utils.handleApiError
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class UserAddressFragment :
    BaseFragment<FragmentUserAddressBinding>(FragmentUserAddressBinding::inflate) {

    private val userData = getDataUser()!!
    private val viewModel: AddressViewModel by viewModels()
    lateinit var adapter: AdapterAddress
    lateinit var recyclerView: RecyclerView

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        loadListAddress()
        initRecyclerView()
        initSwipeRefreshLayout()
    }

    private fun initSwipeRefreshLayout() {
        binding.swipeRefreshLayout.setOnRefreshListener {
            loadListAddress()
        }
    }

    private fun initRecyclerView() {
        adapter = AdapterAddress(requireContext())
        recyclerView = binding.recListProduct
        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(requireContext())
    }

    private fun loadListAddress() {
        viewModel.requestListUserAddress(RequestAddress(userData.id_pelanggan.toString()))
        viewModel.getListAddress.observe(viewLifecycleOwner) {
            hideLoading()
            when (it) {
                is NetworkResult.Success -> {
                    val data = it.data!!.data
                    Log.d("data current address", "$data")
                    adapter.differ.submitList(data)
                }

                is NetworkResult.Loading -> {
                    showLoading()
                }

                is NetworkResult.Error -> {
                    handleApiError(it.message)
                }
            }
        }
    }

    private fun showLoading() {
        binding.progressBar.isVisible = true
    }

    private fun hideLoading() {
        binding.swipeRefreshLayout.isRefreshing = false
        binding.progressBar.isVisible = false
    }

}