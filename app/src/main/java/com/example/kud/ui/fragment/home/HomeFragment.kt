package com.example.kud.ui.fragment.home

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.appcompat.widget.SearchView
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.kud.R
import com.example.kud.data.adapter.AdapterBanner
import com.example.kud.data.adapter.AdapterData
import com.example.kud.data.adapter.AdapterCategory
import com.example.kud.data.model.DataXXX
import com.example.kud.databinding.FragmentHomeBinding
import com.example.kud.ui.base.BaseFragment
import com.example.kud.ui.fragment.RecyclerViewClickListener
import com.example.kud.ui.viewModel.HomeViewModel
import com.example.kud.utils.NetworkResult
import com.example.kud.utils.TokenManager
import com.example.kud.utils.handleApiError
import com.smarteist.autoimageslider.SliderView
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

//    lateinit var sliderView: SliderView

    lateinit var adapterBanner: AdapterBanner


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        toolbarInit()

        binding.textView7.setOnClickListener {
            findNavController().navigate(R.id.action_homeFragment_to_semuaProdukFragment)
        }

//        sliderView = binding.imageSlider

        imageUrl = ArrayList()

        imageUrl =
            (imageUrl + "https://practice.geeksforgeeks.org/_next/image?url=https%3A%2F%2Fmedia.geeksforgeeks.org%2Fimg-practice%2Fbanner%2Fdsa-self-paced-thumbnail.png&w=1920&q=75") as ArrayList<String>
        imageUrl =
            (imageUrl + "https://practice.geeksforgeeks.org/_next/image?url=https%3A%2F%2Fmedia.geeksforgeeks.org%2Fimg-practice%2Fbanner%2Fdata-science-live-thumbnail.png&w=1920&q=75") as ArrayList<String>
        imageUrl =
            (imageUrl + "https://practice.geeksforgeeks.org/_next/image?url=https%3A%2F%2Fmedia.geeksforgeeks.org%2Fimg-practice%2Fbanner%2Ffull-stack-node-thumbnail.png&w=1920&q=75") as ArrayList<String>

        adapterBanner = AdapterBanner(imageUrl)

//        sliderView.autoCycleDirection = SliderView.LAYOUT_DIRECTION_LTR
//
//        sliderView.setSliderAdapter(adapterBanner)
//
//
//        sliderView.scrollTimeInSec = 3
//
//        sliderView.isAutoCycle = true
//
//        sliderView.startAutoCycle()

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

    private fun toolbarInit() {
        binding.toolbarItems.searchView.setOnQueryTextListener(object :
            SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                Toast.makeText(requireContext(), "$query", Toast.LENGTH_SHORT).show()
                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                // Do something when user changes text
                return true
            }
        })

        binding.toolbarItems.searchView.setOnQueryTextFocusChangeListener { _, hasFocus ->
            if (hasFocus) {
                Toast.makeText(requireContext(), "sedang aktif", Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(requireContext(), "tidak aktif", Toast.LENGTH_SHORT).show()
            }
        }

//        binding.toolbarItems.menu.setOnClickListener {
//            Toast.makeText(requireContext(), "menu", Toast.LENGTH_SHORT).show()
//        }
    }

    private fun pindah() {
        findNavController().navigate(R.id.action_homeFragment_to_detailFragment)
    }

    override fun onItemClicked(view: View, data: DataXXX) {
        val dataName = data.nama
//        val dataCreateBy = data.created_by
//        val dataCreateAt = data.created_at
//        val dataUpdateAt = data.updated_at
//        val dataUpdateBy = data.updated_by
        val dataImage = data.foto
        val dataPrice = data.harga
        val dataDescription = data.deskripsi
        val stockDetail = data.stok
        val idCategory = data.id_kategori
        val idDrug = data.id_obat
        val sendData = DataXXX(
//            dataCreateAt,
//            dataCreateBy,
            dataDescription,
            dataImage,
            dataPrice,
            idCategory,
            idDrug,
            dataName,
            stockDetail,
//            dataUpdateAt,
//            dataUpdateBy
        )
        val action = HomeFragmentDirections.actionHomeFragmentToDetailFragment(sendData)
        findNavController().navigate(action)


    }


}