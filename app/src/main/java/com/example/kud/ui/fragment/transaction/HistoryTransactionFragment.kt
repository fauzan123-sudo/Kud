package com.example.kud.ui.fragment.transaction

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.kud.data.adapter.AdapterHistoryTransaction
import com.example.kud.data.model.transaction.response.history.Data
import com.example.kud.databinding.FragmentHistoryTransactionBinding
import com.example.kud.ui.base.BaseFragment
import com.example.kud.ui.viewModel.TransactionViewModel
import com.example.kud.utils.NetworkResult
import com.example.kud.utils.getDataUser
import com.example.kud.utils.handleApiError
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HistoryTransactionFragment :
    BaseFragment<FragmentHistoryTransactionBinding>(FragmentHistoryTransactionBinding::inflate),
    AdapterHistoryTransaction.ItemListener {

    private val dataUser = getDataUser()!!
    lateinit var adapter: AdapterHistoryTransaction
    private lateinit var recyclerView: RecyclerView
    private val viewModel: TransactionViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initRec()
        loadData()

    }

    private fun loadData() {
        viewModel.requestHistoryTransaction(dataUser.id_pelanggan)
        viewModel.getHistoryTransaction.observe(viewLifecycleOwner) {
            binding.progressBar.isVisible = false
            when (it) {
                is NetworkResult.Success -> {
                    val response = it.data!!
                    val data = response.data
                    adapter.differ.submitList(data)
                }

                is NetworkResult.Loading -> {
                    binding.progressBar.isVisible = true
                }

                is NetworkResult.Error -> {
                    handleApiError(it.message)
                    Log.e("error", "${it.message}")
                }
            }
        }
    }

    private fun initRec() {
        adapter = AdapterHistoryTransaction()
        adapter.listener = this
        recyclerView = binding.rvHistoryTransaction
        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(requireContext())
    }

    override fun itemClick(data: Data) {
        val action =
            HistoryTransactionFragmentDirections.actionHistoryTransactionFragmentToDetailTransactionFragment(
                data.order_id, "history_transaction"
            )
        findNavController().navigate(action)
    }
}