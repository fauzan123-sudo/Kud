package com.example.kud.data.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.kud.R
import com.example.kud.data.model.transaction.response.history.Data
import com.example.kud.databinding.ItemHistoryTransactionBinding

class AdapterHistoryTransaction(
    val context: Context,
) : RecyclerView.Adapter<AdapterHistoryTransaction.ViewHolder>() {

    inner class ViewHolder(val binding: ItemHistoryTransactionBinding) :
        RecyclerView.ViewHolder(binding.root)


    private val differCallback = object : DiffUtil.ItemCallback<Data>() {
        override fun areItemsTheSame(
            oldItem: Data,
            newItem: Data
        ): Boolean {
            return oldItem == newItem
        }

        override fun areContentsTheSame(
            oldItem: Data,
            newItem: Data
        ): Boolean {
            return oldItem == newItem
        }
    }

    val differ = AsyncListDiffer(this, differCallback)
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = ViewHolder(
        ItemHistoryTransactionBinding.inflate(LayoutInflater.from(parent.context), parent, false)
    )

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val myPosition = differ.currentList[position]
        with(holder) {
            binding.tvOrderId.text = myPosition.order_id
            binding.tvTotal.text = myPosition.total_harga
            binding.tvDescription.text = myPosition.produk
            binding.tvStatus.text = myPosition.status

            when (myPosition.status) {
                "Dikemas" -> {
                    binding.tvStatus.setBackgroundResource(R.drawable.background_rounded_warning)
                }
                "Dikirim" -> {
                    binding.tvStatus.setBackgroundResource(R.drawable.background_rounded_warning)
                }

                "Sampai" -> {
                    binding.tvStatus.setBackgroundResource(R.drawable.background_rounded_blue)
                }
                else ->{
                    binding.tvStatus.setBackgroundResource(R.drawable.background_rounded_blue)
                }

            }
        }
    }

    override fun getItemCount() = differ.currentList.size


}