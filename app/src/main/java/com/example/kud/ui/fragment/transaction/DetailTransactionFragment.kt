package com.example.kud.ui.fragment.transaction

import android.os.Bundle
import android.view.View
import androidx.activity.OnBackPressedCallback
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.kud.R
import com.example.kud.databinding.FragmentDetailTransactionBinding
import com.example.kud.ui.base.BaseFragment
import com.example.kud.ui.viewModel.TransactionViewModel
import com.example.kud.utils.NetworkResult
import dagger.hilt.android.AndroidEntryPoint
import java.text.NumberFormat
import java.util.Locale

@AndroidEntryPoint
class DetailTransactionFragment :
    BaseFragment<FragmentDetailTransactionBinding>(FragmentDetailTransactionBinding::inflate) {

    private val viewModel: TransactionViewModel by viewModels()
    private val args: DetailTransactionFragmentArgs by navArgs()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setUpData()
        handleBack()
    }

    private fun setUpData() {
        viewModel.requestDetailTransaction(args.transactionCode)
        viewModel.detailTransactionResponse.observe(viewLifecycleOwner) {
            when (it) {
                is NetworkResult.Success -> {
                    dismissLoadingDialog()
                    val response = it.data!!
                    val data = response.data
                    binding.apply {
                        tvAddress.text = data.alamat_pengiriman
                        tvDate.text = data.tanggal
                        tvStatus.text = data.status
                        tvProduct.text = data.produk
                        tvOrderId.text = "Order Id " + data.order_id
                        val totalPrice = NumberFormat.getNumberInstance(Locale.US)
                            .format(data.total_harga.toInt())
                            .replace(",", ".")
                        tvTotal.text = "Total : Rp. $totalPrice"
                    }
                }

                is NetworkResult.Loading -> {
                    showLoadingDialog()
                }

                is NetworkResult.Error -> {
                    dismissLoadingDialog()
                    showErrorDialog(it.message!!)
                }
            }
        }
    }

    private fun handleBack() {
        if (args.thisNavigation != "history_transaction") {
            val callback = object : OnBackPressedCallback(true) {
                override fun handleOnBackPressed() {
                    findNavController().navigate(R.id.action_detailTransactionFragment_to_historyTransactionFragment)

                }
            }
            requireActivity().onBackPressedDispatcher.addCallback(viewLifecycleOwner, callback)
        }
    }

}