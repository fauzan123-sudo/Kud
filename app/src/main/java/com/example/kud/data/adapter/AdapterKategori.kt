package com.example.kud.data.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.example.kud.R
import com.example.kud.data.model.DataX
import com.example.kud.data.model.DataXX
import kotlinx.android.synthetic.main.data_kategori.view.*

class AdapterKategori : RecyclerView.Adapter<AdapterKategori.ViewHolder>() {

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)

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
        LayoutInflater.from(parent.context).inflate(R.layout.data_kategori, parent, false)
    )

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val dataPosition = differ.currentList[position]
        holder.itemView.itemName.text = dataPosition.nama
//        holder.itemView.imageCategory.load(dataPosition.)
    }

    override fun getItemCount(): Int {
        return differ.currentList.size
    }

}