package com.example.kud.ui.fragment.profile

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
//import com.example.kud.data.repository.SafeApiCaller
import com.example.kud.databinding.FragmentProfileBinding
import com.example.kud.ui.base.BaseFragment
import com.example.kud.ui.viewModel.LoginViewModel
import com.example.kud.ui.viewModel.ProfileViewModel
import com.example.kud.utils.NetworkResult
import com.example.kud.utils.handleApiError
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ProfileFragment : BaseFragment<FragmentProfileBinding>(FragmentProfileBinding::inflate) {

    private val viewModel: ProfileViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
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