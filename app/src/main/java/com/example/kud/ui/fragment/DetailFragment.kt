package com.example.kud.ui.fragment

import android.app.Dialog
import android.content.res.ColorStateList
import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.bumptech.glide.Glide
import com.example.kud.R
import com.example.kud.data.model.CheckOut
import com.example.kud.databinding.FragmentDetailBinding
import com.example.kud.ui.viewModel.CheckOutViewModel
import com.example.kud.utils.Constants.IMAGE_OBAT
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import dagger.hilt.android.AndroidEntryPoint
import java.text.NumberFormat
import java.util.*

@AndroidEntryPoint
class DetailFragment : BottomSheetDialogFragment() {
    private var _binding: FragmentDetailBinding? = null
    private val binding get() = _binding!!
    private val viewModel: CheckOutViewModel by viewModels()
    private val args by navArgs<DetailFragmentArgs>()

    var listCheckOut: ArrayList<CheckOut> = ArrayList()

    override fun getTheme() = R.style.BottomSheetDialogTheme

    override fun onStart() {
        super.onStart()

//        view?.viewTreeObserver?.addOnGlobalLayoutListener {
//            val behavior = BottomSheetBehavior.from(requireView().parent as View)
//            behavior.peekHeight = 2500
//        }
//        (dialog as BottomSheetDialog).behavior.peekHeight = getBottomSheetDialogDefaultHeight()
//        dialog?.let {
//            val bottomSheet =
//                it.findViewById<View>(com.google.android.material.R.id.design_bottom_sheet)
//            bottomSheet.layoutParams.height = ViewGroup.LayoutParams.MATCH_PARENT
//        }

    }

//    private fun getBottomSheetDialogDefaultHeight(): Int {
//        val displayMetrics = DisplayMetrics()
//        (context as Activity?)?.windowManager?.defaultDisplay?.getMetrics(displayMetrics)
//        return displayMetrics.heightPixels * 110 / 100
//    }

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

//        Paper.init(requireContext())
//        listCheckOut = Paper.book().read(keyName, ArrayList())!!

        binding.addCart.setOnClickListener {
            findNavController().navigate(R.id.action_detailFragment_to_checkOutFragment)
        }


        val myArgs = args.detailProduct
        with(binding) {
            with(myArgs) {
                detailDescription.text = deskripsi
                txtNameProduct.text = nama
                totalPrice.text = harga
                stockItem.text = "jumlah Stok : $stok"
            }
        }


        val totalPrice = NumberFormat.getNumberInstance(Locale.US)
            .format(myArgs.harga!!.toInt())
            .replace(",", ".")
        binding.totalPrice.text = "Rp. $totalPrice"
        val picture = myArgs.foto
        Glide.with(requireContext()).load("$IMAGE_OBAT$picture")
            .into(binding.imgDetailBarang)

        binding.minus.backgroundTintList = ColorStateList.valueOf(Color.RED)
        var counter = 1
        binding.plus.setOnClickListener {
            if (counter != myArgs.stok) {
                val textAmount = binding.amount.text.toString().toInt()
                binding.plus.isEnabled = textAmount <= myArgs.stok!!
                binding.minus.isEnabled = textAmount >= 1
                counter++
                binding.amount.setText(counter.toString())

                val total = myArgs.harga!!.toInt() * counter
                val totalPrice = NumberFormat.getNumberInstance(Locale.US)
                    .format(total)
                    .replace(",", ".")

                binding.priceProduct.text = "Rp. $totalPrice"
            }
        }

        binding.addToCart.setOnClickListener {
//            CoroutineScope(Dispatchers.IO).launch {
                val amount = binding.amount.text.toString().toInt()
                val data = myArgs.foto?.let { it1 ->
                    CheckOut(
                        0,
                        it1,
                        myArgs.nama,
                        myArgs.id_kategori.toString(),
                        myArgs.harga.toInt(),
                        amount,
                        myArgs.stok
                    )
                }
//                if (data != null) {
//                    listCheckOut.add(data)
//                }
//                //inserting data to paper database
//                Paper.book().write(keyName, listCheckOut)
                if (data != null) {
                    viewModel.insertData(data)
                }
//            }
        }

        binding.minus.setOnClickListener {
            if (counter != 1) {
                val textAmount = binding.amount.text.toString().toInt()
                binding.minus.isEnabled = textAmount > 0
                counter--
                binding.amount.setText(counter.toString())
                val total = myArgs.harga!!.toInt() * counter
                val totalPrice = NumberFormat.getNumberInstance(Locale.US)
                    .format(total)
                    .replace(",", ".")

                binding.priceProduct.text = "Rp. $totalPrice"
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}

