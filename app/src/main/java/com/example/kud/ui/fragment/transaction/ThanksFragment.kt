package com.example.kud.ui.fragment.transaction

import android.os.Bundle
import android.view.View
import com.example.kud.databinding.FragmentThanksBinding
import com.example.kud.ui.base.BaseFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ThanksFragment : BaseFragment<FragmentThanksBinding>(FragmentThanksBinding::inflate) {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

    }

}