package com.example.kud.ui.fragment

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.kud.data.adapter.AdapterCheck
import com.example.kud.data.adapter.AdapterCheckOut
import com.example.kud.data.model.CheckOut
import com.example.kud.databinding.FragmentCheckOutBinding
import com.example.kud.ui.base.BaseFragment
import com.example.kud.ui.viewModel.CheckOutViewModel
import com.example.kud.utils.Helper
import dagger.hilt.android.AndroidEntryPoint
import java.text.NumberFormat
import java.util.*


@AndroidEntryPoint
class CheckOutFragment : BaseFragment<FragmentCheckOutBinding>(FragmentCheckOutBinding::inflate) {
    private var mList = mutableListOf<String>()
    private lateinit var adapterCheckOut: AdapterCheckOut
    private val viewModel: CheckOutViewModel by viewModels()

    private lateinit var recyclerview: RecyclerView

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        adapterCheckOut = AdapterCheckOut(requireContext())

        recyclerview = binding.recCheckOut
        recyclerview.adapter = adapterCheckOut
        recyclerview.layoutManager = LinearLayoutManager(requireContext())
        recyclerview.setHasFixedSize(true)

        adapterCheckOut.listener = object : DeleteItemCheckOut {

            override fun deleteItemCheckOut(position: Int, data: CheckOut) {

                viewModel.deleteUser(data)
                adapterCheckOut.differ.currentList.removeAt(position)
//                adapterCheckOut.notifyItemRemoved(position)
                adapterCheckOut.notifyDataSetChanged()
            }

            override fun upsert(data: CheckOut) {
                viewModel.insertData(data)
            }

            override fun grandTotal(total: Int) {
                val totalPrice = NumberFormat.getNumberInstance(Locale.US)
                    .format(total)
                    .replace(",", ".")
                binding.priceTotal.text = "Rp. $totalPrice"
            }

            override fun grandTotalItem(total: Int) {
                binding.itemsTotal.text = total.toString()
            }

//            override fun updateTotal(allData: MutableList<CheckOut>) {
//                var sum = 0
//                for (i in 0 until allData.size) {
//                    sum += (allData[i].amountItem!! * allData[i].priceItem!!)                           //12000 //24000
//                }
////                Toast.makeText(requireContext(), "$sum", Toast.LENGTH_SHORT).show()
////                tv_total.text = sum.toString()
//            }
        }

//        var total = 0
//        for (i in 0 until adapterCheckOut.itemCount) {
//            val text =  recyclerview.findViewHolderForAdapterPosition(i)!!.itemView.findViewById(R.id.text1) as TextView
//            total += text.text.toString().toInt()
//
//            binding.priceTotal.text = total.toString()
//        }


        viewModel.readData.observe(viewLifecycleOwner) {
            adapterCheckOut.differ.submitList(it)
            mList.add(it.toString())
            totalCost(it)
        }

    }

    private fun totalCost(data: List<CheckOut>?) {
        var total = 0
        var items = 0
        for (item in data!!) {
            total += (item.priceItem!!.toInt() * item.amountItem)
            items += item.amountItem
        }

        binding.priceTotal.text = Helper().gantiRupiah(total)
        binding.itemsTotal.text = items.toString()

    }


}