package com.example.kud.ui.fragment.detail

import android.app.Dialog
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.bumptech.glide.Glide
import com.example.kud.R
import com.example.kud.data.model.checkOut.request.RequestList
import com.example.kud.data.model.detail.request.RequestToCart
import com.example.kud.data.model.detail.request.RequestToCheckOut
import com.example.kud.databinding.FragmentDetailBinding
import com.example.kud.ui.viewModel.DetailViewModel
import com.example.kud.utils.Constants.IMAGE_OBAT
import com.example.kud.utils.NetworkResult
import com.example.kud.utils.getDataUser
import com.example.kud.utils.handleApiError
import com.example.kud.utils.visible
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import dagger.hilt.android.AndroidEntryPoint
import java.text.NumberFormat
import java.util.*

@AndroidEntryPoint
class DetailFragment : BottomSheetDialogFragment() {
    private var _binding: FragmentDetailBinding? = null
    private val binding get() = _binding!!
    private val viewModel: DetailViewModel by viewModels()
    private val args by navArgs<DetailFragmentArgs>()

    private val userData = getDataUser()!!

    override fun getTheme() = R.style.BottomSheetDialogTheme

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {

        return super.onCreateDialog(savedInstanceState).apply {
            window?.setDimAmount(0.0f) // Set dim amount here
            setOnShowListener {
                val bottomSheet =
                    findViewById<View>(com.google.android.material.R.id.design_bottom_sheet) as FrameLayout
//                bottomSheet.setBackgroundResource(android.R.color.transparent)
            }
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = FragmentDetailBinding.inflate(inflater, container, false)
        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val myArgs = args.detailProduct
        Log.d("data", "$myArgs")

        loadDataDetail(myArgs.id_obat)

        binding.addCheckOut.setOnClickListener {
            val qty = binding.amount.text.toString()
            addToCheckOut(userData.id_pelanggan, myArgs.id_obat, qty)
        }

        binding.addToCart.setOnClickListener {
            val userId = userData.id_pelanggan
            val drugId = args.detailProduct.id_obat
            val data =
                RequestToCart(userId.toString(), drugId.toString(), binding.amount.text.toString())
            viewModel.addToCartRequest(data)
            viewModel.addToCart.observe(viewLifecycleOwner) {
                hideLoading()
                when (it) {
                    is NetworkResult.Success -> {
                        Toast.makeText(
                            requireContext(),
                            "Successfully add in cart item ",
                            Toast.LENGTH_SHORT
                        ).show()
                    }

                    is NetworkResult.Loading -> {
                        showLoading()
                    }

                    is NetworkResult.Error -> {
                        handleApiError(it.message)
                        Log.d("error add to cart", "${it.message}")
                    }
                }
            }
////            CoroutineScope(Dispatchers.IO).launch {
//            val amount = binding.amount.text.toString().toInt()
//            val data = myArgs.foto?.let { it1 ->
//                CheckOut(
//                    0,
//                    it1,
//                    myArgs.nama,
//                    myArgs.id_kategori.toString(),
//                    myArgs.harga.toInt(),
//                    amount,
//                    myArgs.stok
//                )
//            }
////                if (data != null) {
////                    listCheckOut.add(data)
////                }
////                //inserting data to paper database
////                Paper.book().write(keyName, listCheckOut)
//            if (data != null) {
//                viewModel.insertData(data)
//            }
////            }
        }

//        with(binding) {
//            with(myArgs) {
//                Log.d("data", "$myArgs")
//                detailDescription.text = deskripsi
//                txtNameProduct.text = nama
//                totalPrice.text = harga
//                stockItem.text = "jumlah Stok : $stok"
//            }
//        }


//        val totalPrice = NumberFormat.getNumberInstance(Locale.US)
//            .format(myArgs.harga!!.toInt())
//            .replace(",", ".")
//        binding.totalPrice.text = "Rp. $totalPrice"
//        val picture = myArgs.foto
//        Glide.with(requireContext()).load("$IMAGE_OBAT$picture")
//            .into(binding.imgDetailBarang)
//
//        binding.minus.backgroundTintList = ColorStateList.valueOf(Color.RED)
        var counter = 1
        binding.plus.setOnClickListener {
            if (counter != myArgs.stok.toInt()) {
                val textAmount = binding.amount.text.toString().toInt()
                binding.plus.isEnabled = textAmount <= myArgs.stok!!.toInt()
                binding.minus.isEnabled = textAmount >= 1
                counter++
                binding.amount.text = counter.toString()

                val total = myArgs.harga!!.toInt() * counter
                val totalPrice = NumberFormat.getNumberInstance(Locale.US)
                    .format(total)
                    .replace(",", ".")

                binding.priceProduct.text = "Rp. $totalPrice"
            }
        }


//
        binding.minus.setOnClickListener {
            if (counter != 1) {
                val textAmount = binding.amount.text.toString().toInt()
                binding.minus.isEnabled = textAmount > 0
                counter--
                binding.amount.text = counter.toString()
                val total = myArgs.harga!!.toInt() * counter
                val totalPrice = NumberFormat.getNumberInstance(Locale.US)
                    .format(total)
                    .replace(",", ".")

                binding.priceProduct.text = "Rp. $totalPrice"
            }
        }
    }

    private fun addToCheckOut(userId: Int, drugId: Int?, qty: String) {
        val data = RequestToCheckOut(userId.toString(), drugId.toString(), qty)
        viewModel.requestToCheckOut(data)
        viewModel.addDataCheckOut.observe(viewLifecycleOwner) {
            hideLoading()
            when (it) {
                is NetworkResult.Success -> {
                    val dataDetail = args.detailProduct
                    val response = it.data!!.data
                    val dataToCheckOut =
                        RequestList(userData.id_pelanggan.toString(), 2, response.id_keranjang.toString())
                    val action = DetailFragmentDirections.actionDetailFragmentToCheckOutFragment(
                        dataToCheckOut
//                        arrayOf(
//                            dataAddToCheckOut
//                        )
                    )
                    findNavController().navigate(action)
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

    private fun loadDataDetail(drugId: Int?) {
        viewModel.requestDetailDrug(drugId!!)
        viewModel.getDetailDrug.observe(viewLifecycleOwner) {
            hideLoading()
            when (it) {
                is NetworkResult.Success -> {
                    val response = it.data!!.data.detail
                    with(binding) {
                        detailDescription.text = response.deskripsi
                        txtNameProduct.text = response.nama
                        totalPrice.text = response.harga
                        stockItem.text = "jumlah Stok : ${response.stok}"
                        amount.text = "1"
                        Glide.with(requireActivity())
                            .load(IMAGE_OBAT+response.foto)
//                            .placeholder(R.drawable.no_image)
                            .error(R.drawable.no_image)
                            .into(imgDetailBarang)

                    }
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

    private fun showLoading() {
        binding.progressBar.visible(true)
    }

    private fun hideLoading() {
        binding.progressBar.visible(false)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}

