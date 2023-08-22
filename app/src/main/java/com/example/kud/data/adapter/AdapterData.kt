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
import com.example.kud.utils.Constants.IMAGE_OBAT
import kotlinx.android.synthetic.main.data_item.view.*
import java.text.NumberFormat
import java.util.*

class AdapterData(val context: Context) : RecyclerView.Adapter<AdapterData.ViewHolder>() {

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
    }
    var listener: RecyclerViewClickListener? = null
    private val differCallback = object : DiffUtil.ItemCallback<DataXXX>() {
        override fun areItemsTheSame(oldItem: DataXXX, newItem: DataXXX): Boolean {
            return oldItem.id_obat == newItem.id_obat
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

//class MyAdapter(
//    items: List<Item>,
//    onItemClick: ((Item) -> Unit)? = null
//) : BaseAdapter<Item, ItemLayoutBinding>(
//    items,
//    ItemLayoutBinding::inflate,
//    onItemClick
//) {
//
//    override fun onBindData(item: Item, binding: ItemLayoutBinding) {
//        binding.itemId.text = item.id.toString()
//        binding.itemName.text = item.name
//        binding.itemDescription.text = item.description
//    }
//}

//import android.view.LayoutInflater
//import android.view.ViewGroup
//import androidx.recyclerview.widget.RecyclerView
//import androidx.viewbinding.ViewBinding
//
//abstract class BaseAdapter<T, VB : ViewBinding>(
//    private val items: List<T>,
//    private val bindingInflater: (LayoutInflater, ViewGroup, Boolean) -> VB,
//    private val onItemClick: ((T) -> Unit)? = null
//) : RecyclerView.Adapter<BaseAdapter<T, VB>.ViewHolder>() {
//
//    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
//        val inflater = LayoutInflater.from(parent.context)
//        val binding = bindingInflater(inflater, parent, false)
//        return ViewHolder(binding)
//    }
//
//    override fun getItemCount(): Int = items.size
//
//    inner class ViewHolder(val binding: VB) : RecyclerView.ViewHolder(binding.root) {
//        init {
//            onItemClick?.let { click ->
//                binding.root.setOnClickListener {
//                    click(items[adapterPosition])
//                }
//            }
//        }
//    }
//
//    fun getItem(position: Int): T = items[position]
//
//    fun getItems(): List<T> = items
//
//    abstract fun onBindData(item: T, binding: VB)
//
//    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
//        val item = items[position]
//        onBindData(item, holder.binding)
//    }
//}

//todo cara penggunaan
//myAdapter = MyAdapter(itemList) { selectedItem ->
//}



