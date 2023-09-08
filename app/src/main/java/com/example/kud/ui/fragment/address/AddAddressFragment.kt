package com.example.kud.ui.fragment.address

import android.os.Bundle
import android.view.View
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import cn.pedant.SweetAlert.SweetAlertDialog
import com.example.kud.R
import com.example.kud.data.model.address.request.RequestAddOrEdit
import com.example.kud.databinding.FragmentAddAddressBinding
import com.example.kud.ui.base.BaseFragment
import com.example.kud.ui.viewModel.AddressViewModel
import com.example.kud.utils.NetworkResult
import com.example.kud.utils.getDataUser
import com.example.kud.utils.handleApiError
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class AddAddressFragment :
    BaseFragment<FragmentAddAddressBinding>(FragmentAddAddressBinding::inflate) {

    private val dataUser = getDataUser()!!
    private val viewModel: AddressViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initToolbar()

    }

    private fun initToolbar() {
        binding.toolbar.setOnMenuItemClickListener {
            when (it.itemId) {
                R.id.action_save -> {
                    addAddress()
                    return@setOnMenuItemClickListener true
                }
                else -> {
                    return@setOnMenuItemClickListener false
                }
            }
        }
    }

    private fun addAddress() {
        val title = binding.etJudul.text.toString()
        val fullAddress = binding.etNamaLengkap.text.toString()
        viewModel.requestAddAddress(
            RequestAddOrEdit(
                dataUser.id_pelanggan.toString(),
                title,
                fullAddress
            )
        )
        viewModel.getAddAddress.observe(viewLifecycleOwner) {
            binding.progressBar.isVisible = false
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
                    binding.progressBar.isVisible = true
                }

                is NetworkResult.Error -> {
                    handleApiError(it.message)
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