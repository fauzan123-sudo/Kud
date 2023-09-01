package com.example.kud.ui.fragment.cart

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.kud.data.adapter.AdapterCart
import com.example.kud.data.model.cart.list.DataX
import com.example.kud.data.model.cart.request.RequestCheckUnCheck
import com.example.kud.data.model.cart.request.RequestDeleteDrug
import com.example.kud.data.model.cart.request.RequestListCart
import com.example.kud.data.model.cart.request.RequestPlusMinus
import com.example.kud.data.model.detail.DetailProduct
import com.example.kud.databinding.FragmentCartBinding
import com.example.kud.ui.base.BaseFragment
import com.example.kud.ui.viewModel.CartViewModel
import com.example.kud.utils.Helper
import com.example.kud.utils.NetworkResult
import com.example.kud.utils.getDataUser
import com.example.kud.utils.handleApiError
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class CartFragment : BaseFragment<FragmentCartBinding>(FragmentCartBinding::inflate),
    AdapterCart.ItemClickListener {

    private val cartViewModel: CartViewModel by viewModels()
    private val dataUser = getDataUser()!!
    lateinit var adapter: AdapterCart
    lateinit var recyclerView: RecyclerView

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        loadListData()
        initRecyclerView()
//        checkOut()

    }

//    private fun checkOut(data: DataX) {
//        with(binding){
//            btnCheckout.setOnClickListener {
//                val dataAddToCheckOut = DetailProduct(
//                    "",
//                    data.image,
//                    data.harga,
//                    data.total,
//                    data.kategori,
//                    data.qty.toInt(),
//                    data.id_obat,
//                    data.nama_obat,
//                    data.stok,
//                    1,
//                    data.id_keranjang
//                )
//                val action = CartFragmentDirections.actionCartFragmentToCheckOutFragment(arrayOf(dataAddToCheckOut))
//                findNavController().navigate(action)
//            }
//        }
//    }

    private fun loadTotal(drugId: Int, status: Int) {
        cartViewModel.requestPlusMinus(
            RequestPlusMinus(
                dataUser.id_pelanggan.toString(),
                status.toString(),
                drugId.toString()
            )
        )
        cartViewModel.getPlusMinus.observe(viewLifecycleOwner) {
            when (it) {
                is NetworkResult.Success -> {
                    val response = it.data!!.data
                    binding.tvGrandTotal.text = Helper().gantiRupiah(response.sub_total)
                }

                is NetworkResult.Loading -> {

                }

                is NetworkResult.Error -> {
                    handleApiError(it.message)
                }
            }
        }
    }

    private fun initRecyclerView() {
        adapter = AdapterCart(requireContext())
        adapter.itemClickListener = this
        recyclerView = binding.recListProduct
        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(requireContext())
    }

    private fun loadListData() {
        val requestList = RequestListCart(dataUser.id_pelanggan.toString(), 1)
        cartViewModel.listOfCart(requestList)
        cartViewModel.getList.observe(viewLifecycleOwner) {
            binding.progressBar.isVisible = false
            when (it) {
                is NetworkResult.Success -> {
                    binding.progressBar.isVisible = false
                    val response = it.data!!.data
                    val data = response.data
                    adapter.differ.submitList(data)
//                    binding.tvGrandTotal.text = Helper().gantiRupiah(response.sub_total)
                }

                is NetworkResult.Loading -> {
                    binding.progressBar.isVisible = true
                }

                is NetworkResult.Error -> {
                    binding.progressBar.isVisible = false
                    Log.e("error", "${it.message}")
                    handleApiError(it.message)
                }
            }
        }
    }

    override fun upsert(drugId: Int?, status: Int, qty: Int) {
        when (status) {
            1 -> {
                loadTotal(drugId!!, status)
                Log.d("button plus", "plus: ")
            }
            2 -> {
                loadTotal(drugId!!, status)
                Log.d("button minus", "minus: ")
            }
            else -> {
                Log.d("nothing to click", "")
            }
        }
    }

    override fun onDeleteButtonClicked(position: Int, drugId: Int) {
        Log.d("delete button", "")
        cartViewModel.requestDeleteDrug(RequestDeleteDrug(dataUser.id_pelanggan, drugId))
        cartViewModel.getDeleteDrug.observe(viewLifecycleOwner) {
            binding.progressBarRec.isVisible = false
            when (it) {
                is NetworkResult.Success -> {
                    val response = it.data!!.data
                    binding.progressBarRec.isVisible = false
                    adapter.deleteItem(position)
                    binding.tvGrandTotal.text = response.sub_total.toString()
                }

                is NetworkResult.Loading -> {
                    binding.progressBarRec.isVisible = true
                }

                is NetworkResult.Error -> {
                    binding.progressBarRec.isVisible = false
                    handleApiError(it.message)
                }
            }
        }
    }

    override fun checkBox(position: Int, cartId: String, data: DataX) {
        Log.d("CheckBox", "Item $cartId dicentang")
        cartViewModel.requestCheckUnCheck(RequestCheckUnCheck(dataUser.id_pelanggan, cartId))
        cartViewModel.getCheckUnCheck.observe(viewLifecycleOwner) {
            when (it) {
                is NetworkResult.Success -> {
                    val response = it.data!!.data
                    binding.tvGrandTotal.text = Helper().gantiRupiah(response.sub_total)

//                    checkOut(data)
                }

                is NetworkResult.Loading -> {

                }

                is NetworkResult.Error -> {
                    handleApiError(it.message)
                }
            }
        }
    }
}