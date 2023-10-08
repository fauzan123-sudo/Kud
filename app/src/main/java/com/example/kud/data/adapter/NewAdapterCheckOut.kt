package com.example.kud.data.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.example.kud.data.model.checkOut.list.DataX
import com.example.kud.databinding.ItemCheckoutBinding
import com.example.kud.utils.Constants.IMAGE_OBAT
import com.example.kud.utils.Helper

class NewAdapterCheckOut(val context: Context) :
    RecyclerView.Adapter<NewAdapterCheckOut.ViewHolder>() {
    private lateinit var binding: ItemCheckoutBinding

    //    var listener: CheckOutHelper? = null
    var itemClickListener: ItemClickListener? = null

    inner class ViewHolder : RecyclerView.ViewHolder(binding.root) {
        fun setData(item: DataX) {
//            var totalItem = item.amountItem
            val myPosition = differ.currentList[absoluteAdapterPosition]
            binding.apply {
//                    textView9.text = Helper().gantiRupiah(amountItem * priceItem!!)
                totalItems.text = item.qty
                drugName.text = item.nama_obat
                drugPrice.text = Helper().gantiRupiah(item.harga)
                drugType.text = item.kategori
                imageProduct.load(IMAGE_OBAT + item.image)

                deleteItem.setOnClickListener {
                    itemClickListener?.onDeleteButtonClicked(layoutPosition, item)
                }

                btnPlus.setOnClickListener {

//                    Log.d("plus", "setData: ")
//                    totalItem++
//                    item.amountItem = totalItem
//                    totalItems.text = totalItem.toString()
//                    textView9.text = Helper().gantiRupiah(item.priceItem!! * totalItem)
                    itemClickListener?.upsert(myPosition.id_obat, 1)
                }

                btnMinus.setOnClickListener {
//                    Log.d("minus", "setData: ")
//                    totalItem--
//                    if (totalItem <= 1) return@setOnClickListener
//                    item.amountItem = totalItem
//                    totalItems.text = totalItem.toString()
//                    textView9.text = Helper().gantiRupiah(item.priceItem!! * totalItem)
                    itemClickListener?.upsert(myPosition.id_obat, 2)
                }
//                totalItems.addTextChangedListener(object : TextWatcher {
//                    override fun beforeTextChanged(
//                        p0: CharSequence?, p1: Int, p2: Int, p3: Int
//                    ) {
//
//                    }
//
//                    override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
////                        listener?.upsert(myPosition)
//                    }
//
//                    override fun afterTextChanged(p0: Editable?) {
////                        listener?.upsert(myPosition)
//                    }
//
//                })
            }
        }
    }

    private val differCallback = object : DiffUtil.ItemCallback<DataX>() {
        override fun areItemsTheSame(oldItem: DataX, newItem: DataX): Boolean {
            return oldItem.id_obat == newItem.id_obat
        }

        override fun areContentsTheSame(oldItem: DataX, newItem: DataX): Boolean {
            return oldItem.id_obat == newItem.id_obat
        }
    }

    val differ = AsyncListDiffer(this, differCallback)

    interface ItemClickListener {
        fun upsert(drugId: Int?, status: Int)
        fun onDeleteButtonClicked(position: Int, data: DataX)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        binding = ItemCheckoutBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder()
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.setData(differ.currentList[position])
        holder.setIsRecyclable(false)

    }

    override fun getItemCount(): Int {
        return differ.currentList.size
    }

}