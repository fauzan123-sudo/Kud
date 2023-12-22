package com.example.kud.data.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.kud.data.model.address.list.Data
import com.example.kud.databinding.ItemAddressBinding

class AdapterAddress : RecyclerView.Adapter<AdapterAddress.ViewHolder>() {

    var listener: ItemListener? = null
    var selectedPosition = RecyclerView.NO_POSITION

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
        val data = differ.currentList
        val myPosition = data[position]
        with(holder) {
            with(binding) {
                checkBox.isChecked = selectedPosition == position || myPosition.default == "1"
                tvTitleAddress.text = myPosition.nama
                tvAddress.text = myPosition.alamat

                checkBox.setOnCheckedChangeListener { _, isChecked ->
                    if (isChecked && selectedPosition != position) {
                        myPosition.default = "1"
                        for (address in differ.currentList) {
                            if (address != myPosition) {
                                address.default = "0"
                            }
                        }
                        selectedPosition = position
                        notifyDataSetChanged()
                        listener?.itemClick(myPosition)
                    }
                }
            }
        }
    }

    override fun getItemCount() = differ.currentList.size
}