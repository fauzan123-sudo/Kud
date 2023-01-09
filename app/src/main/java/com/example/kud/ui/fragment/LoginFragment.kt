package com.example.kud.ui.fragment

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.example.kud.R
import com.example.kud.data.model.LoginRequest
import com.example.kud.databinding.FragmentLoginBinding
import com.example.kud.ui.activity.MainActivity
import com.example.kud.ui.base.BaseFragment
import com.example.kud.ui.viewModel.LoginViewModel
import com.example.kud.utils.*
import com.example.kud.utils.Constans.TAG
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
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



//        lifecycleScope.launch {
//            val data = userPreferences.read()
//            if (data != null) requireActivity().startNewActivity(MainActivity::class.java)
//            Log.d(TAG, " read token user  $data")
//        }
//        userPreferences.accessToken.asLiveData().observe(requireActivity()) {
//            readToken = it.toString()
//        }

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
                    tokenManager.saveToken(it.data!!.user.access_token)
//                    lifecycleScope.launch {
//                        userPreferences.saveAccessTokens(it.data!!.user.access_token)
//                    }
                    requireActivity().startNewActivity(MainActivity::class.java)
                }
                is NetworkResult.Error -> {
                    val error = it.message.toString()
                    handleApiError(error)
                    Log.d(TAG,(it.message.toString()))

                }
                is NetworkResult.Loading -> {
                    binding.progressBar.isVisible = true
                }
            }
        }
    }

}