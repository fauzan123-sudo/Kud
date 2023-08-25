package com.example.kud.ui.fragment.auth

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.fragment.app.activityViewModels
import com.example.kud.data.model.LoginRequest
import com.example.kud.databinding.FragmentLoginBinding
import com.example.kud.ui.activity.MainActivity
import com.example.kud.ui.base.BaseFragment
import com.example.kud.ui.viewModel.LoginViewModel
import com.example.kud.utils.*
import com.example.kud.utils.Constants.TAG
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class LoginFragment : BaseFragment<FragmentLoginBinding>(FragmentLoginBinding::inflate) {

    private val viewModel by activityViewModels<LoginViewModel>()

    @Inject
    lateinit var tokenManager: TokenManager

    @Inject
    lateinit var userPreferences: UserPreferences

    private var readToken: String = ""

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        if (tokenManager.getToken() != null) {
            requireActivity().startNewActivity(MainActivity::class.java)
        }

        Log.d(TAG + "logins", readToken)

        binding.btnLogin.setOnClickListener {
            Helper.hideKeyboard(it)
            val userName = binding.edtEmail.text.toString().trim()
            val password = binding.edtPassword.text.toString().trim()
            val loginRequest = LoginRequest(userName, password)
            Log.d(TAG, "onViewCreated: $loginRequest")
            when {
                userName.isEmpty() -> Toast.makeText(
                    requireContext(),
                    "Please fill in field username",
                    Toast.LENGTH_SHORT
                ).show()
                password.isEmpty() -> Toast.makeText(
                    requireContext(),
                    "Please fill in field password",
                    Toast.LENGTH_SHORT
                ).show()
                else -> viewModel.login(loginRequest)

            }

        }
        bindObservers()
    }

    private fun bindObservers() {
        viewModel.userResponseLiveData.observe(viewLifecycleOwner) {
            binding.progressBar.isVisible = false
            when (it) {
                is NetworkResult.Success -> {

                    Log.d(TAG, "here success")
                    val statusResponse = it.data!!.status
                    if (statusResponse == 200) {
                        val userToken = it.data.data.access_token
                        tokenManager.saveToken(userToken)
                        requireActivity().startNewActivity(MainActivity::class.java)

                    }else{
                        Toast.makeText(requireContext(), "$statusResponse", Toast.LENGTH_SHORT).show()
                        handleApiError(statusResponse.toString())
                    }
                }
                is NetworkResult.Error -> {
                    val error = it.message.toString()
                    handleApiError(error)
                    Log.d(TAG, (it.message.toString()))

                }
                is NetworkResult.Loading -> {
                    binding.progressBar.isVisible = true
                }
            }
        }
    }
}