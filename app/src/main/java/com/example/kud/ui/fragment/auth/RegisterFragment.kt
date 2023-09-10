package com.example.kud.ui.fragment.auth

//import com.example.kud.databinding.FragmentRegisterBinding
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import com.example.kud.databinding.FragmentRegisterBinding
import com.example.kud.ui.base.BaseFragment
import com.example.kud.ui.viewModel.AuthViewModel
import com.example.kud.utils.NetworkResult
import com.example.kud.utils.handleApiError
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class RegisterFragment : BaseFragment<FragmentRegisterBinding>(FragmentRegisterBinding::inflate) {

    private val viewModel: AuthViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val fullNameEditText = binding.etFullName
        val emailEditText = binding.etEmail
        val passwordEditText = binding.etPassword
        val confirmPasswordEditText = binding.etPasswordConfirm
        val addressEditText = binding.etAddress
        val registerButton = binding.btnOrder

        registerButton.setOnClickListener {
            val fullName = fullNameEditText.text.toString()
            val email = emailEditText.text.toString()
            val password = passwordEditText.text.toString()
            val confirmPassword = confirmPasswordEditText.text.toString()
            val address = addressEditText.text.toString()
            if (isValidInput(fullName, email, password, confirmPassword, address)) {
                loadRegister(fullName, email, password, address)
            } else {
                if (fullName.isEmpty()) {
                    fullNameEditText.error = "Nama Lengkap harus diisi"
                }
                if (email.isEmpty()) {
                    emailEditText.error = "Email harus diisi"
                }
                if (password.isEmpty()) {
                    passwordEditText.error = "Password harus diisi"
                }
                if (confirmPassword.isEmpty()) {
                    confirmPasswordEditText.error = "Konfirmasi Password harus diisi"
                }
                if (address.isEmpty()) {
                    addressEditText.error = "Alamat harus diisi"
                }
                if (password != confirmPassword) {
                    passwordEditText.error = "Password dan Konfirmasi Password tidak cocok"
                    confirmPasswordEditText.error = "Password dan Konfirmasi Password tidak cocok"
                }
            }
        }
    }

    private fun isValidInput(
        fullName: String,
        email: String,
        password: String,
        confirmPassword: String,
        address: String
    ): Boolean {
        return fullName.isNotEmpty() && email.isNotEmpty() &&
                password.isNotEmpty() && confirmPassword.isNotEmpty() && address.isNotEmpty() &&
                password == confirmPassword
    }

    private fun loadRegister(fullName: String, email: String, password: String, address: String) {
        viewModel.registerUser(fullName, email, password, address)
        viewModel.getRegisterUser.observe(viewLifecycleOwner) {
            binding.progressBar.isVisible = false
            dismissLoadingDialog()
            when (it) {
                is NetworkResult.Success -> {
                    viewModel.getRegisterUser.removeObservers(viewLifecycleOwner)
                    val registerResponse = it.data!!
                    if (registerResponse.msg == "Success") {
                        showSuccessDialog()

                    }else{
                        showErrorDialog(registerResponse.msg)
                    }
                }

                is NetworkResult.Loading -> {
                    showLoadingDialog()
                }

                is NetworkResult.Error -> {
                    viewModel.getRegisterUser.removeObservers(viewLifecycleOwner)
                    handleApiError(it.message)
                    showErrorDialog(it.message!!)
                    Log.e("error", "${it.message}")
                }
            }
        }
    }
}