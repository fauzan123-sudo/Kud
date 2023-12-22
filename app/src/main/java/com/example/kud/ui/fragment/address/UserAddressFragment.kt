package com.example.kud.ui.fragment.address

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.kud.R
import com.example.kud.data.adapter.AdapterAddress
import com.example.kud.data.model.address.list.Data
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
    BaseFragment<FragmentUserAddressBinding>(FragmentUserAddressBinding::inflate),
    AdapterAddress.ItemListener {

    private val userData = getDataUser()!!
    private val viewModel: AddressViewModel by viewModels()
    lateinit var adapter: AdapterAddress
    private lateinit var recyclerView: RecyclerView
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        loadListAddress()
        initRecyclerView()
        initSwipeRefreshLayout()
        toolbarInit()
        addAddress()
    }

    private fun addAddress() {
        binding.toolbar.setOnMenuItemClickListener {
            when (it.itemId) {
                R.id.actionAdd -> {
                    Toast.makeText(requireContext(), "hai", Toast.LENGTH_SHORT).show()
                    findNavController().navigate(R.id.action_userAddressFragment_to_addAddressFragment)
                    return@setOnMenuItemClickListener true
                }

                else -> {
                    return@setOnMenuItemClickListener false
                }
            }
        }
    }

    private fun toolbarInit() {
        val toolbar = binding.toolbar
//        (requireActivity() as AppCompatActivity).setSupportActionBar(toolbar)
//        toolbar.inflateMenu(R.menu.menu_add)
    }

    private fun initSwipeRefreshLayout() {
        binding.swipeRefreshLayout.setOnRefreshListener {
            loadListAddress()
        }
    }

    private fun initRecyclerView() {
        adapter = AdapterAddress()
        adapter.listener = this
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

    override fun itemClick(data: Data) {
        Log.d("data", "$data")
        try {
            val action =
                UserAddressFragmentDirections.actionUserAddressFragmentToChangeAddressFragment(data)
            findNavController().navigate(action)
        } catch (e: Exception) {
            Toast.makeText(requireContext(), "$e", Toast.LENGTH_SHORT).show()
            Log.e("error findNav", "$e")
        }
    }

}