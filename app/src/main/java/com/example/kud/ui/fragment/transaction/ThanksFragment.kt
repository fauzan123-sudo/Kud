package com.example.kud.ui.fragment.transaction

import android.os.Bundle
import android.view.View
import androidx.activity.OnBackPressedCallback
import androidx.navigation.fragment.findNavController
import com.example.kud.R
import com.example.kud.databinding.FragmentThanksBinding
import com.example.kud.ui.base.BaseFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ThanksFragment : BaseFragment<FragmentThanksBinding>(FragmentThanksBinding::inflate) {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        backPress()
        binding.btnDetailTransaction.setOnClickListener {
            backPress()
        }

    }

    private fun backPress() {
        val callback = object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                findNavController().navigate(R.id.action_thanksFragment_to_homeFragment)
                findNavController().popBackStack(R.id.homeFragment, false)
            }
        }
        requireActivity().onBackPressedDispatcher.addCallback(viewLifecycleOwner, callback)
    }
}