package com.example.kud.data.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.example.kud.R
import com.example.kud.data.model.DataXXX
import com.example.kud.ui.fragment.RecyclerViewClickListener
import com.example.kud.utils.Constans.IMAGE_OBAT
import kotlinx.android.synthetic.main.data_item.view.*
import java.text.NumberFormat
import java.util.*

class AdapterData(val context: Context) : RecyclerView.Adapter<AdapterData.ViewHolder>() {

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)
    var listener: RecyclerViewClickListener? = null
    private val differCallback = object : DiffUtil.ItemCallback<DataXXX>() {
        override fun areItemsTheSame(oldItem: DataXXX, newItem: DataXXX): Boolean {
            return oldItem == newItem
        }

        override fun areContentsTheSame(oldItem: DataXXX, newItem: DataXXX): Boolean {
            return oldItem == newItem
        }
    }

    val differ = AsyncListDiffer(this, differCallback)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = ViewHolder(
        LayoutInflater.from(parent.context).inflate(R.layout.data_item, parent, false)
    )

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val dataPosition = differ.currentList[position]
        holder.itemView.itemName.text = dataPosition.nama
        val totalPrice = NumberFormat.getNumberInstance(Locale.US)
            .format(dataPosition.harga!!.toInt())
            .replace(",", ".")
        holder.itemView.amountData.text =  "Rp. $totalPrice"
        holder.itemView.imageItem.load(IMAGE_OBAT+dataPosition.foto)

        holder.itemView.setOnClickListener {
            listener?.onItemClicked(it, dataPosition)
        }
    }

    override fun getItemCount(): Int {
        return differ.currentList.size
    }

}