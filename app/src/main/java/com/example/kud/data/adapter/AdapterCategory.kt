package com.example.kud.data.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.kud.data.model.DataXX
import com.example.kud.data.model.allProduct.response.Data
import com.example.kud.data.model.cart.list.DataX
import com.example.kud.databinding.DataKategoriBinding

class AdapterCategory : RecyclerView.Adapter<AdapterCategory.ViewHolder>() {

    inner class ViewHolder(val binding: DataKategoriBinding) : RecyclerView.ViewHolder(binding.root)

    private val differCallback = object : DiffUtil.ItemCallback<Data>() {
        override fun areItemsTheSame(oldItem: Data, newItem: Data): Boolean {
            return oldItem == newItem
        }

        override fun areContentsTheSame(oldItem: Data, newItem: Data): Boolean {
            return oldItem == newItem
        }
    }

    val differ = AsyncListDiffer(this, differCallback)

    interface ItemClickListener {
        fun upsert(drugId: Int?, status: Int, qty: Int)
//        fun onDeleteButtonClicked(position: Int, drugId: Int)
//        fun checkBox(position: Int, cartId: String, data: DataX)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = ViewHolder(
       DataKategoriBinding.inflate(LayoutInflater.from(parent.context), parent, false)
    )

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val dataPosition = differ.currentList[position]
        holder.binding.itemName.text = dataPosition.nama
    }

    override fun getItemCount(): Int {
        return differ.currentList.size
    }

}