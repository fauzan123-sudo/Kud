package com.example.kud.ui.fragment.transaction

import android.os.Bundle
import android.view.View
import com.example.kud.databinding.FragmentPaymentProofBinding
import com.example.kud.ui.base.BaseFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class PaymentProofFragment :
    BaseFragment<FragmentPaymentProofBinding>(FragmentPaymentProofBinding::inflate) {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }
}