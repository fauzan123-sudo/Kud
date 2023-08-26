package com.example.kud.data.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.kud.R
import com.example.kud.data.model.DataXX
import com.example.kud.databinding.DataKategoriBinding

class AdapterKategori : RecyclerView.Adapter<AdapterKategori.ViewHolder>() {

    inner class ViewHolder(val binding: DataKategoriBinding) : RecyclerView.ViewHolder(binding.root)

    private val differCallback = object : DiffUtil.ItemCallback<DataXX>() {
        override fun areItemsTheSame(oldItem: DataXX, newItem: DataXX): Boolean {
            return oldItem == newItem
        }

        override fun areContentsTheSame(oldItem: DataXX, newItem: DataXX): Boolean {
            return oldItem == newItem
        }
    }

    val differ = AsyncListDiffer(this, differCallback)

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