package com.example.kud.data.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.kud.R
import com.example.kud.data.model.allProduct.response.Data
import com.example.kud.databinding.DataKategoriBinding

class AdapterCategory(val context: Context) : RecyclerView.Adapter<AdapterCategory.ViewHolder>() {
    var itemClickListener: ItemClickListener? = null
    private var selectedPosition = 0

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
        fun getCategory(drugId: String)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = ViewHolder(
        DataKategoriBinding.inflate(LayoutInflater.from(parent.context), parent, false)
    )

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val dataPosition = differ.currentList[position]
        holder.binding.itemName.text = dataPosition.nama

        if (holder.absoluteAdapterPosition == selectedPosition) {
            holder.binding.root.setBackgroundColor(
                ContextCompat.getColor(
                    context,
                    R.color.primary
                )
            )
        } else {
            holder.binding.root.setBackgroundColor(
                ContextCompat.getColor(
                    context,
                    android.R.color.white
                )
            )
        }

        holder.binding.root.setOnClickListener {
            val previousSelectedPosition = selectedPosition
            selectedPosition = holder.absoluteAdapterPosition
            notifyItemChanged(previousSelectedPosition)
            notifyItemChanged(selectedPosition)

            itemClickListener?.getCategory(dataPosition.id_kategori)

        }

    }

    override fun getItemCount(): Int {
        return differ.currentList.size
    }

}