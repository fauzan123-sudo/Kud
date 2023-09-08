package com.example.kud.ui.fragment.home

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.activity.OnBackPressedCallback
import androidx.appcompat.widget.SearchView
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import cn.pedant.SweetAlert.SweetAlertDialog
import com.example.kud.R
import com.example.kud.data.adapter.AdapterBanner
import com.example.kud.data.adapter.AdapterCategory
import com.example.kud.data.adapter.AdapterData
import com.example.kud.data.model.home.list.Data
import com.example.kud.databinding.FragmentHomeBinding
import com.example.kud.ui.base.BaseFragment
import com.example.kud.ui.fragment.RecyclerViewClickListener
import com.example.kud.ui.viewModel.HomeViewModel
import com.example.kud.utils.NetworkResult
import com.example.kud.utils.TokenManager
import com.example.kud.utils.handleApiError
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class HomeFragment : BaseFragment<FragmentHomeBinding>(FragmentHomeBinding::inflate),
    RecyclerViewClickListener {

    @Inject
    lateinit var tokenManager: TokenManager

    private val viewModel: HomeViewModel by viewModels()
    private lateinit var adapterData: AdapterData
    private lateinit var adapterCategorise: AdapterCategory

    private var mList = mutableListOf<String>()
    private lateinit var recyclerviewItems: RecyclerView

    private lateinit var imageUrl: ArrayList<String>

    lateinit var adapterBanner: AdapterBanner


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        toolbarInit()
        onBackPress()

        binding.textView7.setOnClickListener {
            findNavController().navigate(R.id.action_homeFragment_to_semuaProdukFragment)
        }

        imageUrl = ArrayList()

        imageUrl =
            (imageUrl + "https://practice.geeksforgeeks.org/_next/image?url=https%3A%2F%2Fmedia.geeksforgeeks.org%2Fimg-practice%2Fbanner%2Fdsa-self-paced-thumbnail.png&w=1920&q=75") as ArrayList<String>
        imageUrl =
            (imageUrl + "https://practice.geeksforgeeks.org/_next/image?url=https%3A%2F%2Fmedia.geeksforgeeks.org%2Fimg-practice%2Fbanner%2Fdata-science-live-thumbnail.png&w=1920&q=75") as ArrayList<String>
        imageUrl =
            (imageUrl + "https://practice.geeksforgeeks.org/_next/image?url=https%3A%2F%2Fmedia.geeksforgeeks.org%2Fimg-practice%2Fbanner%2Ffull-stack-node-thumbnail.png&w=1920&q=75") as ArrayList<String>

        adapterBanner = AdapterBanner(imageUrl)

        adapterData = AdapterData(requireContext())
        adapterCategorise = AdapterCategory()

        adapterData.listener = this

        recyclerviewItems = binding.recItemsHome
        recyclerviewItems.adapter = adapterData
        recyclerviewItems.layoutManager = GridLayoutManager(requireContext(), 2)

        viewModel.drug()
        viewModel.getDrug.observe(viewLifecycleOwner) {
            binding.progressBar.isVisible = false
            when (it) {
                is NetworkResult.Success -> {
                    Log.d("success", "data is exist")
                    val status = it.data!!.status
                    if (status == 200) {
                        val data = it.data.data
                        adapterData.differ.submitList(data)
                        mList.add(it.toString())
                    } else {
                        handleApiError(status.toString())
                        Log.d("is error ", "data is not exist and ${it.message}")
                    }
                }

                is NetworkResult.Loading -> {
                    binding.progressBar.isVisible = true
                }

                is NetworkResult.Error -> {
                    Log.d("is error ", "data is not exist and ${it.message}")
                    handleApiError(it.message)
                }
            }
        }
    }

    private fun onBackPress() {
        val callback = object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                SweetAlertDialog(requireContext(), SweetAlertDialog.WARNING_TYPE)
                    .setTitleText("Konfirmasi")
                    .setContentText("Apakah Anda yakin ingin keluar dari aplikasi?")
                    .setConfirmText("Ya")
                    .setConfirmClickListener { sDialog ->
                        sDialog.dismissWithAnimation()
                        requireActivity().finish()
                    }
                    .setCancelText("Tidak")
                    .setCancelClickListener { sDialog ->
                        sDialog.dismissWithAnimation()
                    }
                    .show()
            }
        }

        requireActivity().onBackPressedDispatcher.addCallback(viewLifecycleOwner, callback)
    }

    private fun toolbarInit() {
        binding.toolbarItems.searchView.setOnQueryTextListener(object :
            SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                searchDrug(query)
                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                // Do something when user changes text
                return true
            }
        })

        binding.toolbarItems.searchView.setOnQueryTextFocusChangeListener { _, hasFocus ->
            if (hasFocus) {
//                Toast.makeText(requireContext(), "sedang aktif", Toast.LENGTH_SHORT).show()
            } else {
//                Toast.makeText(requireContext(), "tidak aktif", Toast.LENGTH_SHORT).show()
            }
        }

//        binding.toolbarItems.menu.setOnClickListener {
//            Toast.makeText(requireContext(), "menu", Toast.LENGTH_SHORT).show()
//        }
    }

    private fun searchDrug(query: String?) {
        viewModel.requestSearchDrug(query!!, "")
        viewModel.searchDrug.observe(viewLifecycleOwner) {
            binding.progressBar.isVisible = false
            when (it) {
                is NetworkResult.Success -> {
                    val response = it.data!!
                    val data = response.data
                    adapterData.differ.submitList(data)
                }

                is NetworkResult.Loading -> {
                    binding.progressBar.isVisible = true
                }

                is NetworkResult.Error -> {
                    handleApiError(it.message)
                }
            }
        }
    }

    override fun onItemClicked(view: View, data: Data) {
        val dataName = data.nama
        val dataImage = data.foto
        val dataPrice = data.harga
        val dataDescription = data.deskripsi
        val stockDetail = data.stok.toInt()
        val idCategory = data.id_kategori
        val idDrug = data.id_obat
        val sendData = Data(
            dataDescription,
            idDrug,
            dataImage,
            dataPrice,
            idCategory,
            dataName,
            stockDetail.toString(),
        )
        val action = HomeFragmentDirections.actionHomeFragmentToDetailFragment(sendData)
        findNavController().navigate(action)


    }


}