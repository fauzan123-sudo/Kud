package com.example.kud.data.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.kud.data.model.transaction.response.history.Data
import com.example.kud.databinding.ItemHistoryTransactionBinding

class AdapterHistoryTransaction : RecyclerView.Adapter<AdapterHistoryTransaction.ViewHolder>() {

    var listener: ItemListener? = null

    interface ItemListener {
        fun itemClick(data: Data)
    }


    inner class ViewHolder(val binding: ItemHistoryTransactionBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(data: Data) {
            with(binding) {
                tvOrderId.text = data.order_id
                tvTotal.text = data.total_harga
                tvDescription.text = data.produk
                tvStatus.text = data.status

                when (data.status) {
                    "Dikemas" -> {
                        tvStatus.setBackgroundResource(com.example.kud.R.drawable.background_rounded_warning)
                    }

                    "Dikirim" -> {
                        tvStatus.setBackgroundResource(com.example.kud.R.drawable.background_rounded_primary)
                    }

                    "Sampai" -> {
                        tvStatus.setBackgroundResource(com.example.kud.R.drawable.background_rounded_blue)
                    }

                    else -> {
                        tvStatus.setBackgroundResource(com.example.kud.R.drawable.background_rounded_blue)
                    }

                }
                root.setOnClickListener {
                    listener?.itemClick(data)
                }
            }
        }
    }


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
        holder.bind(myPosition)
    }

    override fun getItemCount() = differ.currentList.size

}