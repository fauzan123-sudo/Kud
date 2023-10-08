package com.example.kud.data.adapter

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.kud.R
import com.example.kud.data.model.address.list.Data
import com.example.kud.databinding.ItemAddressBinding

class AdapterAddress(
    val context: Context,
) : RecyclerView.Adapter<AdapterAddress.ViewHolder>() {

    var listener: ItemListener? = null

    interface ItemListener {
        fun itemClick(data: Data)
    }

    inner class ViewHolder(val binding: ItemAddressBinding) :
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
        ItemAddressBinding.inflate(LayoutInflater.from(parent.context), parent, false)
    )

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val myPosition = differ.currentList[position]
        with(holder) {
            binding.tvTitleAddress.text = myPosition.nama
            binding.tvAddress.text = myPosition.alamat

            val isDefault = myPosition.default == "1"

           if (isDefault){
               holder.binding.root.setBackgroundColor(
                   ContextCompat.getColor(
                       context,
                       R.color.primary
                   )
               )
           }else{
               holder.binding.root.setBackgroundColor(
                   ContextCompat.getColor(
                       context,
                       R.color.white
                   )
               )
           }

            holder.itemView.setOnClickListener {
                listener?.itemClick(myPosition)
            }

            Log.d("address", "${myPosition.alamat}")
        }
    }

    override fun getItemCount(): Int {
        return differ.currentList.size
    }
}