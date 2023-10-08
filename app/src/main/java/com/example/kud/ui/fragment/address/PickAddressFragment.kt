package com.example.kud.ui.fragment.address

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import cn.pedant.SweetAlert.SweetAlertDialog
import com.example.kud.data.adapter.AdapterAddress
import com.example.kud.data.model.address.list.Data
import com.example.kud.data.model.address.request.RequestAddress
import com.example.kud.data.model.address.request.RequestEditAddress
import com.example.kud.databinding.FragmentPickAddressBinding
import com.example.kud.ui.base.BaseFragment
import com.example.kud.ui.viewModel.AddressViewModel
import com.example.kud.utils.NetworkResult
import com.example.kud.utils.getDataUser
import com.example.kud.utils.handleApiError
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class PickAddressFragment :
    BaseFragment<FragmentPickAddressBinding>(FragmentPickAddressBinding::inflate),
    AdapterAddress.ItemListener {

    private val viewModel: AddressViewModel by viewModels()
    lateinit var adapter: AdapterAddress
    private lateinit var recyclerView: RecyclerView
    private val userData = getDataUser()!!

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        loadListAddress()
        initRecyclerView()
    }

    private fun initRecyclerView() {
        adapter = AdapterAddress(requireContext())
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
        viewModel.requestEditAddress(
            RequestEditAddress(
                data.id.toString(),
                data.nama,
                data.alamat ?: "",
                1
            )
        )
        viewModel.getEdit.observe(viewLifecycleOwner) {
            when (it) {
                is NetworkResult.Success -> {
                    val response = it.data!!
                    if (response.status == 1) {
                        showSuccessAlert()
                    } else {
                        showErrorAlert()
                    }
                }

                is NetworkResult.Loading -> {
                    showLoadingDialog()
                }

                is NetworkResult.Error -> {
                    showErrorDialog(it.message ?: "")
                }
            }
        }
    }

    private fun showSuccessAlert() {
        SweetAlertDialog(requireContext(), SweetAlertDialog.SUCCESS_TYPE)
            .setTitleText("Berhasil")
            .setContentText("Data berhasil ditambahkan")
            .setConfirmText("OK")
            .setConfirmClickListener {
                findNavController().popBackStack()
                it.dismissWithAnimation()
            }
            .show()
    }

    private fun showErrorAlert() {
        SweetAlertDialog(requireContext(), SweetAlertDialog.ERROR_TYPE)
            .setTitleText("Gagal")
            .setContentText("Terjadi kesalahan saat menyimpan data")
            .setConfirmText("OK")
            .setConfirmClickListener { it.dismissWithAnimation() }
            .show()
    }

}