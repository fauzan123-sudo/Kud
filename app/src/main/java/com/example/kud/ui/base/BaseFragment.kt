package com.example.kud.ui.base

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.viewbinding.ViewBinding
import cn.pedant.SweetAlert.SweetAlertDialog

abstract class BaseFragment<VB : ViewBinding>(
    private val bindingInflater: (inflater: LayoutInflater) -> VB
) : Fragment() {

    var _binding: VB? = null

    val binding: VB
        get() = _binding as VB

    var loadingDialog: SweetAlertDialog? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = bindingInflater.invoke(inflater)
        if(_binding == null)
            throw IllegalArgumentException("Binding cannot be null")
        return binding.root
    }

    fun showSuccessDialog() {
        SweetAlertDialog(requireContext(), SweetAlertDialog.SUCCESS_TYPE)
            .setTitleText("Berhasil")
            .setContentText("Gambar berhasil diunggah")
            .show()
    }

    fun showLoadingDialog() {
        if (loadingDialog == null || !loadingDialog!!.isShowing) {
            loadingDialog = SweetAlertDialog(requireContext(), SweetAlertDialog.PROGRESS_TYPE)
                .setTitleText("Sedang Memuat")
                .setContentText("Mohon tunggu...")
            loadingDialog?.show()
        }
    }

    fun showErrorDialog(errorMessage: String) {
        SweetAlertDialog(requireContext(), SweetAlertDialog.ERROR_TYPE)
            .setTitleText("Terjadi Kesalahan")
            .setContentText(errorMessage)
            .showCancelButton(true)
            .show()
    }

    fun dismissLoadingDialog() {
        loadingDialog?.dismiss()
    }


}