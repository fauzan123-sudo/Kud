package com.example.kud.ui.fragment.profile

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import cn.pedant.SweetAlert.SweetAlertDialog
//import com.example.kud.data.repository.SafeApiCaller
import com.example.kud.databinding.FragmentProfileBinding
import com.example.kud.ui.activity.LoginActivity
import com.example.kud.ui.base.BaseFragment
import com.example.kud.ui.viewModel.AuthViewModel
import com.example.kud.ui.viewModel.LoginViewModel
import com.example.kud.ui.viewModel.ProfileViewModel
import com.example.kud.utils.NetworkResult
import com.example.kud.utils.TokenManager
import com.example.kud.utils.handleApiError
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class ProfileFragment : BaseFragment<FragmentProfileBinding>(FragmentProfileBinding::inflate) {

    @Inject
    lateinit var token: TokenManager
    private val viewModel: ProfileViewModel by viewModels()
    private val authViewModel: AuthViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        loadDataProfile()

        binding.btnLogout.setOnClickListener {
            logOut()
        }
    }

    private fun logOut() {
        SweetAlertDialog(requireContext(), SweetAlertDialog.WARNING_TYPE)
            .setTitleText("Keluar")
            .setContentText("Apakah Anda yakin ingin keluar dari aplikasi?")
            .setConfirmText("Ya, Keluar")
            .setConfirmClickListener { sDialog ->
                sDialog.dismissWithAnimation()
                performLogout()
            }
            .setCancelButton("Batal") { sDialog -> sDialog.dismissWithAnimation() }
            .show()
    }

    private fun performLogout() {
        authViewModel.logoutRequest()
        authViewModel.logoutLiveData.observe(viewLifecycleOwner) {
            binding.progressBar.isVisible = false
            when (it) {
                is NetworkResult.Success -> {
                    val status = it.data!!.status
                    Log.d("status", "$status")
                    if (status == 200) {
                    startActivity(Intent(requireContext(), LoginActivity::class.java))
                    token.deleteToken()
                    } else {
                        Toast.makeText(requireContext(), "gagal logout", Toast.LENGTH_SHORT).show()
                    }
                }

                is NetworkResult.Loading -> binding.progressBar.isVisible = true

                is NetworkResult.Error -> {
                    Log.d("error Logout", "${it.message}")
                    handleApiError(it.message)
                }
            }
        }
    }

    private fun loadDataProfile() {
        Log.d("token", "${token.getToken()}")
        viewModel.profileUser()
        viewModel.profileResponseLiveData.observe(viewLifecycleOwner) {
            binding.progressBar.isVisible = false
            when (it) {
                is NetworkResult.Success -> {
                    val response = it.data!!.data
                    binding.tvEmail.text = response.email
                    binding.tvNama.text = response.nama
                }

                is NetworkResult.Loading -> binding.progressBar.isVisible = true

                is NetworkResult.Error -> {
                    Log.d("error profile", "${it.message}")
                    handleApiError(it.message)
                }
            }
        }
    }
}