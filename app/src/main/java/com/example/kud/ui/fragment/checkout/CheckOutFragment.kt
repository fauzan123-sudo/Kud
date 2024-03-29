package com.example.kud.ui.fragment.checkout

import android.content.ClipboardManager
import android.content.Context
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.kud.R
import com.example.kud.data.adapter.NewAdapterCheckOut
import com.example.kud.data.model.checkOut.list.DataX
import com.example.kud.data.model.checkOut.request.RequestAddress
import com.example.kud.data.model.checkOut.request.RequestList
import com.example.kud.data.model.checkOut.request.RequestPlusMinus
import com.example.kud.data.model.transaction.request.RequestAddPayment
import com.example.kud.databinding.FragmentCheckOutBinding
import com.example.kud.ui.base.BaseFragment
import com.example.kud.ui.viewModel.CheckOutViewModel
import com.example.kud.ui.viewModel.TransactionViewModel
import com.example.kud.utils.NetworkResult
import com.example.kud.utils.getDataUser
import com.example.kud.utils.handleApiError
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class CheckOutFragment : BaseFragment<FragmentCheckOutBinding>(FragmentCheckOutBinding::inflate),
    NewAdapterCheckOut.ItemClickListener {
    private lateinit var adapterCheckOut: NewAdapterCheckOut
    private lateinit var recyclerview: RecyclerView
    private val args by navArgs<CheckOutFragmentArgs>()
    private val userData = getDataUser()!!
    private val viewModel: CheckOutViewModel by viewModels()
    private val transactionViewModel: TransactionViewModel by viewModels()
    private var userLocation = ""

    private var isJenisPengirimanSelected = false
    private var isJenisPembayaranSelected = false

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        adapterCheckOut = NewAdapterCheckOut(requireContext())
        adapterCheckOut.itemClickListener = this
        with(binding) {
            recyclerview = recCheckOut
            recyclerview.adapter = adapterCheckOut
            recyclerview.layoutManager = LinearLayoutManager(requireContext())
//            recyclerview.setHasFixedSize(true)
        }

        loadData(args)
        subTotal(args)
        pickToSend()
        grandTotal()
        changeAddress()
//        getUserAddress()

        binding.btnCancel.setOnClickListener {
            findNavController().popBackStack()
        }

        binding.lvCopy.setOnClickListener {
            val textToCopy = binding.tvNoRek.text.toString()
            val clipboardManager =
                requireActivity().getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
            clipboardManager.text = textToCopy

            Toast.makeText(requireContext(), "Teks telah disalin", Toast.LENGTH_SHORT).show()
        }

        binding.btnPayment.isEnabled = binding.rgJenisPengiriman.checkedRadioButtonId != -1 &&
                binding.rgJenisPembayaran.checkedRadioButtonId != -1

        binding.btnPayment.setOnClickListener {
            val selectedJenisPengirimanId = binding.rgJenisPengiriman.checkedRadioButtonId
            val selectedJenisPembayaranId = binding.rgJenisPembayaran.checkedRadioButtonId

            val selectedJenisPengirimanText = when (selectedJenisPengirimanId) {
                binding.rbAmbilSendiri.id -> "1"
                binding.rbCod.id -> "2"
                else -> "Tidak dipilih"
            }

            val selectedJenisPembayaranText = when (selectedJenisPembayaranId) {
                binding.rbTunai.id -> "1"
                binding.rbTransfer.id -> "2"
                else -> "Tidak dipilih"
            }

            checkOutPayment(selectedJenisPengirimanText, selectedJenisPembayaranText)
        }

    }

    private fun checkOutPayment(
        selectedJenisPengirimanText: String,
        selectedJenisPembayaranText: String
    ) {
        transactionViewModel.requestAddPayment(
            RequestAddPayment(
                args.dataCheckOut.id_keranjang,
                userData.id_pelanggan.toString(),
                userLocation,
                selectedJenisPengirimanText,
                selectedJenisPembayaranText
            )
        )
        transactionViewModel.getAddPayment.observe(viewLifecycleOwner) {
            binding.progressBar.isVisible = false
            when (it) {
                is NetworkResult.Success -> {
                    val response = it.data!!
                    if (response.status == 1) {
                        if (selectedJenisPembayaranText == "1") {
                            findNavController().navigate(R.id.action_checkOutFragment_to_thanksFragment)
                        } else {
                            val action =
                                CheckOutFragmentDirections.actionCheckOutFragmentToPaymentProofFragment(
                                    " ${response.data.total_bayar}",
                                    response.data.kode_transaksi
                                )
                            findNavController().navigate(action)
                        }

                    } else {
                        Toast.makeText(requireContext(), response.msg, Toast.LENGTH_SHORT).show()
                    }

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

    private fun enablePaymentButton(btnPayment: Button) {
        btnPayment.isEnabled = isJenisPengirimanSelected && isJenisPembayaranSelected
    }

    private fun changeAddress() {
        binding.changeAddress.setOnClickListener {
//            findNavController().navigate(R.id.action_checkOutFragment_to_userAddressFragment)
            findNavController().navigate(R.id.action_checkOutFragment_to_pickAddressFragment)
        }
    }

//    private fun getUserAddress() {
//        loadAddressFromApi()
//    }

    private fun loadAddressFromApi() {
        viewModel.requestUserAddress(RequestAddress(userData.id_pelanggan.toString()))
        viewModel.getUserAddress.observe(viewLifecycleOwner) {
            hideLoading()
            when (it) {
                is NetworkResult.Success -> {
                    val response = it.data!!
                    val data = response.data
                    if (data.isNotEmpty()) {
//                        find for find the 1 element, while filter are used for all element
                        val defaultAddress = data.find { it.default == "1" }

                        if (defaultAddress != null) {
                            binding.txtAddress.text = defaultAddress.alamat
                        } else {
                            val topAddress = data.firstOrNull()
                            binding.txtAddress.text = topAddress?.alamat ?: "Tidak ada alamat"
                        }
//                        val filterDataTrue = data.filter { addressUser ->
//                            addressUser.default == "1"
//                        }
//                                txtAddress.text = dataToSave.alamat
//                                userLocation = dataToSave.id.toString()

//                        if (filterDataTrue.isEmpty()) {
//                            val dataToSave = response[0].copy(status = true)
//                            saveListAddress(listOf(dataToSave))
//                            with(binding) {
//                            }
//                        }
//                        else {
//                            val trueData = filterDataTrue[0]
//                            with(binding) {
//                                txtAddress.text = trueData.alamat
//                                userLocation = trueData.id.toString()
//                            }
//                        }
                    } else {
                        Toast.makeText(requireContext(), "data is Null", Toast.LENGTH_SHORT).show()
                    }
//                    if (filterDataTrue.isNotEmpty()) {
//                        val dataToSave = filterDataTrue[0].copy(status = true)
//                        saveAddress(dataToSave)
//                    } else {
//                        val dataToSave = response[0].copy(status = true)
//                        saveAddress(dataToSave)
//                        val address = getAddress()!!
//                        with(binding){
//                            txtAddress.text = address.alamat
//                            userLocation = address.id.toString()
//                        }
////                        response.forEach { address ->
////                            binding.txtAddress.text = address.alamat
////                            userLocation = address.id.toString()
////                        }
//                    }
                }

                is NetworkResult.Loading -> {
                    showLoading()
                }

                is NetworkResult.Error -> {
                    handleApiError(it.message)
                }
            }
        }
    }

    private fun grandTotal() {
        with(binding) {
            subTotal.addTextChangedListener(object : TextWatcher {
                override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

                }

                override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                    updateGrandTotal()
                }

                override fun afterTextChanged(p0: Editable?) {

                }

            })
            tvOngkosKirim.addTextChangedListener(object : TextWatcher {
                override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

                }

                override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                    updateGrandTotal()
                }

                override fun afterTextChanged(p0: Editable?) {

                }

            })
        }
    }

    private fun updateGrandTotal() {
        with(binding) {
            val subTotal = subTotal.text.toString()
            val shippingCost = tvOngkosKirim.text.toString()
            val total = subTotal.toInt() + shippingCost.toInt()
            tvTotal.text = total.toString()
        }
    }

    private fun pickToSend() {
        with(binding) {
            rgJenisPengiriman.setOnCheckedChangeListener { _, checkedId ->
                isJenisPengirimanSelected = checkedId != -1
                enablePaymentButton(btnPayment)
                val selectedRadioButton = when (checkedId) {
                    binding.rbCod.id -> binding.rbCod
                    else -> binding.rbAmbilSendiri
                }

                if (selectedRadioButton.id == binding.rbCod.id) {
                    binding.tvOngkosKirim.text = "5000"
                    loadAddressFromApi()
                    binding.llAlamatPengiriman.isVisible = true
//                    binding.txtAddress.isVisible = true
                } else {
                    binding.txtAddress.isVisible = false
                    binding.tvOngkosKirim.text = "0"
//                    binding.txtAddress.text = ""
                }
            }

            rgJenisPembayaran.setOnCheckedChangeListener { _, checkedId ->
                isJenisPembayaranSelected = checkedId != -1
                enablePaymentButton(btnPayment)
            }
        }
    }

    private fun subTotal(subTotal: CheckOutFragmentArgs) {

    }

    private fun loadData(dataDetail: CheckOutFragmentArgs) {
        val userId = dataDetail.dataCheckOut.id_pelanggan
        val type = dataDetail.dataCheckOut.tipe
        val cartId = dataDetail.dataCheckOut.id_keranjang
        viewModel.requestList(RequestList(userId, type, cartId))
        viewModel.getData.observe(viewLifecycleOwner) {
            binding.progressBar.isVisible = false
            when (it) {
                is NetworkResult.Success -> {
                    val response = it.data!!.data
                    val data = response.data
                    adapterCheckOut.differ.submitList(data)
                    binding.subTotal.text = response.sub_total
                }

                is NetworkResult.Loading -> {
                    binding.progressBar.isVisible = true
                }

                is NetworkResult.Error -> {
                    handleApiError(it.message)
                }
            }
        }

//        Log.d("data", "$dataDetail")
//        val data = dataDetail.dataCheckOut.toList()
//        val status: Array<DetailProduct> = dataDetail.dataCheckOut
//        status.forEach { product ->
//            val currentStatus = product.status
//            if (currentStatus == 1) {
//                adapterCheckOut.differ.submitList(data)
//                binding.subTotal.text = product.total
//                updateGrandTotal()
//                Log.d("status", "single")
//            } else {
//                adapterCheckOut.differ.submitList(data)
//                binding.subTotal.text = product.total
//                updateGrandTotal()
//                Log.d("status", "from cart")
//            }
//        }
    }

    override fun upsert(drugId: Int?, status: Int) {
        if (status == 1) {
            Log.d("upsert item", "plus")
            val dataPlusMinus =
                RequestPlusMinus(
                    userData.id_pelanggan.toString(),
                    status.toString(),
                    drugId.toString()
                )
            viewModel.requestPlusMinus(dataPlusMinus)
            viewModel.getPlusMinus.observe(viewLifecycleOwner) {
                hideLoading()
                when (it) {
                    is NetworkResult.Success -> {

                    }

                    is NetworkResult.Loading -> {
                        showLoading()
                    }

                    is NetworkResult.Error -> {
                        handleApiError(it.message)
                    }
                }
            }
        } else
            Log.d("upsert item", "minus")

    }

    override fun onDeleteButtonClicked(position: Int, data: DataX) {
        Log.d("delete item", "deleteItemCheckOut: ")
    }

    private fun showLoading() {
        binding.progressBar.isVisible = true
    }

    private fun hideLoading() {
        binding.progressBar.isVisible = false
    }

//    override fun deleteItemCheckOut(position: Int, data: CheckOut) {
//        Log.d("delete item", "deleteItemCheckOut: ")
//    }
//
//    override fun upsert(drugId: Int?, status: Int) {
//        Log.d("upsert item", "")
//    }

}

//class CheckOutFragment : BaseFragment<FragmentCheckOutBinding>(FragmentCheckOutBinding::inflate) {
//    private var mList = mutableListOf<String>()
//    private lateinit var adapterCheckOut: AdapterCheckOut
//    private val viewModel: CheckOutViewModel by viewModels()
//
//    private lateinit var recyclerview: RecyclerView
//
//    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
//        super.onViewCreated(view, savedInstanceState)
//
//        adapterCheckOut = AdapterCheckOut(requireContext())
//
//        recyclerview = binding.recCheckOut
//        recyclerview.adapter = adapterCheckOut
//        recyclerview.layoutManager = LinearLayoutManager(requireContext())
//        recyclerview.setHasFixedSize(true)
//
//        adapterCheckOut.listener = object : DeleteItemCheckOut {
//
//            override fun deleteItemCheckOut(position: Int, data: CheckOut) {
//
//                viewModel.deleteUser(data)
//                adapterCheckOut.differ.currentList.removeAt(position)
////                adapterCheckOut.notifyItemRemoved(position)
//                adapterCheckOut.notifyDataSetChanged()
//            }
//
//            override fun upsert(data: CheckOut) {
//                viewModel.insertData(data)
//            }
//
//            override fun grandTotal(total: Int) {
//                val totalPrice = NumberFormat.getNumberInstance(Locale.US)
//                    .format(total)
//                    .replace(",", ".")
//                binding.priceTotal.text = "Rp. $totalPrice"
//            }
//
//            override fun grandTotalItem(total: Int) {
//                binding.itemsTotal.text = total.toString()
//            }
//
////            override fun updateTotal(allData: MutableList<CheckOut>) {
////                var sum = 0
////                for (i in 0 until allData.size) {
////                    sum += (allData[i].amountItem!! * allData[i].priceItem!!)                           //12000 //24000
////                }
//////                Toast.makeText(requireContext(), "$sum", Toast.LENGTH_SHORT).show()
//////                tv_total.text = sum.toString()
////            }
//        }
//
////        var total = 0
////        for (i in 0 until adapterCheckOut.itemCount) {
////            val text =  recyclerview.findViewHolderForAdapterPosition(i)!!.itemView.findViewById(R.id.text1) as TextView
////            total += text.text.toString().toInt()
////
////            binding.priceTotal.text = total.toString()
////        }
//
//
//        viewModel.readData.observe(viewLifecycleOwner) {
//            adapterCheckOut.differ.submitList(it)
//            mList.add(it.toString())
//            totalCost(it)
//        }
//
//    }
//
//    private fun totalCost(data: List<CheckOut>?) {
//        var total = 0
//        var items = 0
//        for (item in data!!) {
//            total += (item.priceItem!!.toInt() * item.amountItem)
//            items += item.amountItem
//        }
//
//        binding.priceTotal.text = Helper().gantiRupiah(total)
//        binding.itemsTotal.text = items.toString()
//
//    }
//
//
//}