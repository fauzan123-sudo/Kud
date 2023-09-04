package com.example.kud.ui.fragment.auth

//import com.example.kud.databinding.FragmentRegisterBinding
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import cn.pedant.SweetAlert.SweetAlertDialog
import com.example.kud.R
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
            when (it) {
                is NetworkResult.Success -> {
                    viewModel.getRegisterUser.removeObservers(viewLifecycleOwner)
                    val registerResponse = it.data!!
                    if (registerResponse.msg == "Success") {
                        showSuccessDialog()

                    }else{
                        showErrorDialog(registerResponse.msg)
                    }
//                    if (registerResponse.data != null) {
//                        showSuccessDialog()
//                    } else {
//                        val emailResponse = registerResponse.msg.email?.joinToString("\n") ?: ""
//                        val passwordResponse = registerResponse.msg.email?.joinToString("\n") ?: ""
//                        val message = emailResponse + passwordResponse
//                        val errorMessage = "Registrasi gagal:\nEmail: $emailResponse \nPassword: $passwordResponse"
//                        Log.e("Registrasi", errorMessage)
//                        showErrorDialog(errorMessage)
//                    }
                }

                is NetworkResult.Loading -> {
                    binding.progressBar.isVisible = true
                }

                is NetworkResult.Error -> {
                    viewModel.getRegisterUser.removeObservers(viewLifecycleOwner)
                    handleApiError(it.message)
                    Log.e("error", "${it.message}")
                }
            }
        }
    }

    private fun showSuccessDialog() {
        SweetAlertDialog(requireContext(), SweetAlertDialog.SUCCESS_TYPE)
            .setTitleText("Registrasi Berhasil")
            .setContentText("Selamat, Anda telah berhasil terdaftar!")
            .setConfirmText("OK")
            .setConfirmClickListener {
                it.dismissWithAnimation()
                findNavController().navigate(R.id.action_registerFragment_to_loginFragment2)
            }
            .show()
    }

    private fun showErrorDialog(message: String) {
        SweetAlertDialog(requireContext(), SweetAlertDialog.ERROR_TYPE)
            .setTitleText("Registrasi Gagal")
            .setContentText(message)
//            .setCancelText("ok")
//            .setConfirmText("Ok")
//            .setConfirmClickListener {
//                findNavController().navigate(R.id.action_registerFragment_to_loginFragment2)
//            }
            .showCancelButton(true)
            .show()
    }

}