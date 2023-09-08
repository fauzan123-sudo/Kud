package com.example.kud.ui.fragment.address

import android.os.Bundle
import android.view.View
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import cn.pedant.SweetAlert.SweetAlertDialog
import com.example.kud.R
import com.example.kud.data.model.address.request.RequestAddOrEdit
import com.example.kud.databinding.FragmentChangeAdressBinding
import com.example.kud.ui.base.BaseFragment
import com.example.kud.ui.viewModel.AddressViewModel
import com.example.kud.utils.NetworkResult
import com.example.kud.utils.getDataUser
import com.example.kud.utils.handleApiError
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ChangeAddressFragment :
    BaseFragment<FragmentChangeAdressBinding>(FragmentChangeAdressBinding::inflate) {

    private val args: ChangeAddressFragmentArgs by navArgs()
    private val viewModel: AddressViewModel by viewModels()
    private val dataUser = getDataUser()!!

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val data = args.dataAddress
        binding.etJudul.setText(data.nama)
        binding.etNamaLengkap.setText(data.alamat)

        initToolbar()

    }

    private fun savedEditAddress() {
        val title = binding.etJudul.text.toString()
        val fullAddress = binding.etNamaLengkap.text.toString()
        viewModel.requestEditAddress(
            RequestAddOrEdit(
                dataUser.id_pelanggan.toString(),
                title,
                fullAddress
            )
        )
        viewModel.getEdit.observe(viewLifecycleOwner) {
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

    private fun initToolbar() {
        binding.toolbar.setOnMenuItemClickListener {
            when (it.itemId) {
                R.id.action_save -> {
                    savedEditAddress()
                    return@setOnMenuItemClickListener true
                }
                else -> {
                    return@setOnMenuItemClickListener false
                }
            }
        }
    }

    private fun showSuccessAlert() {
        SweetAlertDialog(requireContext(), SweetAlertDialog.SUCCESS_TYPE)
            .setTitleText("Berhasil")
            .setContentText("Data berhasil diubah")
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