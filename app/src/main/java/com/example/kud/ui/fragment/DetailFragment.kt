package com.example.kud.ui.fragment

import android.app.Dialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import com.example.kud.R
import com.example.kud.data.model.CheckOut
import com.example.kud.databinding.FragmentDetailBinding
import com.example.kud.ui.viewModel.CheckOutViewModel
import com.example.kud.ui.viewModel.HomeViewModel
import com.example.kud.utils.NetworkResult
import com.example.kud.utils.handleApiError
import com.example.kud.utils.visible
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import dagger.hilt.android.AndroidEntryPoint
import java.util.*

@AndroidEntryPoint
class DetailFragment : BottomSheetDialogFragment() {
    private var _binding: FragmentDetailBinding? = null
    private val binding get() = _binding!!
    private val viewModel: CheckOutViewModel by viewModels()
    private val homeViewModel: HomeViewModel by viewModels()
    private val args by navArgs<DetailFragmentArgs>()

    var listCheckOut: ArrayList<CheckOut> = ArrayList()

    override fun getTheme() = R.style.BottomSheetDialogTheme

    override fun onStart() {
        super.onStart()

    }

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

        loadData(myArgs.id_obat)

//        addToCart()
//        sellingProduct()


//        Paper.init(requireContext())
//        listCheckOut = Paper.book().read(keyName, ArrayList())!!

//        binding.addCart.setOnClickListener {
//            findNavController().navigate(R.id.action_detailFragment_to_checkOutFragment)
//        }


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
//        var counter = 1
//        binding.plus.setOnClickListener {
//            if (counter != myArgs.stok) {
//                val textAmount = binding.amount.text.toString().toInt()
//                binding.plus.isEnabled = textAmount <= myArgs.stok!!
//                binding.minus.isEnabled = textAmount >= 1
//                counter++
//                binding.amount.setText(counter.toString())
//
//                val total = myArgs.harga!!.toInt() * counter
//                val totalPrice = NumberFormat.getNumberInstance(Locale.US)
//                    .format(total)
//                    .replace(",", ".")
//
//                binding.priceProduct.text = "Rp. $totalPrice"
//            }
//        }
//
//        binding.addToCart.setOnClickListener {
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
//        }
//
//        binding.minus.setOnClickListener {
//            if (counter != 1) {
//                val textAmount = binding.amount.text.toString().toInt()
//                binding.minus.isEnabled = textAmount > 0
//                counter--
//                binding.amount.setText(counter.toString())
//                val total = myArgs.harga!!.toInt() * counter
//                val totalPrice = NumberFormat.getNumberInstance(Locale.US)
//                    .format(total)
//                    .replace(",", ".")
//
//                binding.priceProduct.text = "Rp. $totalPrice"
//            }
//        }
    }

    private fun sellingProduct() {

    }

    private fun addToCart() {

    }

    private fun loadData(idObat: Int?) {
        homeViewModel.detailDrug(idObat!!)
        homeViewModel.getDetailDrug.observe(viewLifecycleOwner) {
            hideLoading()
            when (it) {
                is NetworkResult.Success -> {
                    val response = it.data!!.data.detail
                    with(binding) {
                        detailDescription.text = response.deskripsi
                        txtNameProduct.text = response.nama
                        totalPrice.text = response.harga
                        stockItem.text = "jumlah Stok : ${response.stok}"
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

    fun showLoading() {
        binding.progressBar.visible(true)
    }

    fun hideLoading() {
        binding.progressBar.visible(false)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}

