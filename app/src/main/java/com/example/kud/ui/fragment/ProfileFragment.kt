package com.example.kud.ui.fragment

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.kud.data.adapter.FakeAdapter
import com.example.kud.data.network.FakeApi
import com.example.kud.data.repository.RepositorySafe
import com.example.kud.data.repository.ResultWrapper
import com.example.kud.data.repository.SafeApiCaller
//import com.example.kud.data.repository.SafeApiCaller
import com.example.kud.databinding.FragmentProfileBinding
import com.example.kud.ui.base.BaseFragment
import com.example.kud.ui.viewModel.AlbumViewModel
import com.example.kud.ui.viewModel.FakeApiViewModel
import com.example.kud.ui.viewModel.ViewModelFactory
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ProfileFragment : BaseFragment<FragmentProfileBinding>(FragmentProfileBinding::inflate) {

//            private val viewModel: FakeApiViewModel by viewModels()
    private val viewModel: FakeApiViewModel by viewModels()
    private lateinit var adapter: FakeAdapter
    private val fake = "myFak"

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        adapter = FakeAdapter()

        val recyclerview = binding.recv
        recyclerview.adapter = adapter
        recyclerview.layoutManager = LinearLayoutManager(requireContext())

        viewModel.fake()
        viewModel.fakeApi.observe(viewLifecycleOwner) {
            when (it) {
                is ResultWrapper.Success -> adapter.differ.submitList(it.value)
                is ResultWrapper.GenericError -> {
                    Toast.makeText(
                        requireContext(),
                        "error ${it.code.toString()} ${it.code.toString()}",
                        Toast.LENGTH_SHORT

                    ).show()
                    Log.d(fake, it.toString())
                }
                else -> {
                    Log.d(fake, it.toString())
                    Toast.makeText(requireContext(), it.toString(), Toast.LENGTH_SHORT).show()
                }
            }

        }


    }


}