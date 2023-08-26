package com.example.kud.ui.fragment.auth

import android.os.Bundle
import android.view.View
import com.example.kud.databinding.FragmentRegisterBinding
import com.example.kud.ui.base.BaseFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class RegisterFragment : BaseFragment<FragmentRegisterBinding>(FragmentRegisterBinding::inflate) {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

    }

}