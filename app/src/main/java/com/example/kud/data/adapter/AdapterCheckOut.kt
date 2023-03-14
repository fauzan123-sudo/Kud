package com.example.kud.data.adapter

import android.content.Context
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.example.kud.data.model.CheckOut
import com.example.kud.databinding.ItemCheckoutBinding
import com.example.kud.ui.fragment.DeleteItemCheckOut
import com.example.kud.utils.Constans
import com.example.kud.utils.Helper
import java.text.NumberFormat
import java.util.*

class AdapterCheckOut(val context: Context) : RecyclerView.Adapter<AdapterCheckOut.ViewHolder>() {
    private lateinit var binding: ItemCheckoutBinding
    var listener: DeleteItemCheckOut? = null

    inner class ViewHolder : RecyclerView.ViewHolder(binding.root) {
        fun setData(item: CheckOut) {
            var totalItem = item.amountItem
            val myPosition = differ.currentList[absoluteAdapterPosition]
            binding.apply {
                with(item) {
                    textView9.text = Helper().gantiRupiah(amountItem * priceItem!!)
                    totalItems.text = amountItem.toString()
                    drugName.text = nameItem
                    drugPrice.text = Helper().gantiRupiah("$priceItem")
                    drugType.text = category
                    imageProduct.load(Constans.IMAGE_OBAT + item.imageItem)
                }

                deleteItem.setOnClickListener {
                    listener?.deleteItemCheckOut(layoutPosition, item)
                }

                btnPlus.setOnClickListener {
                    totalItem++
                    item.amountItem = totalItem
                    totalItems.text = totalItem.toString()
                    textView9.text = Helper().gantiRupiah(item.priceItem!! * totalItem)
                    listener?.upsert(myPosition)

                }

                btnMinus.setOnClickListener {
                    totalItem--
                    if (totalItem <= 1) return@setOnClickListener
                    item.amountItem = totalItem
                    totalItems.text = totalItem.toString()
                    textView9.text = Helper().gantiRupiah(item.priceItem!! * totalItem)
                    listener?.upsert(myPosition)
                }

                totalItems.addTextChangedListener(object : TextWatcher {
                    override fun beforeTextChanged(
                        p0: CharSequence?, p1: Int, p2: Int, p3: Int
                    ) {

                    }

                    override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                        listener?.upsert(myPosition)
                    }

                    override fun afterTextChanged(p0: Editable?) {
                        listener?.upsert(myPosition)
                    }

                })


            }
        }
    }

    private val differCallback = object : DiffUtil.ItemCallback<CheckOut>() {
        override fun areItemsTheSame(oldItem: CheckOut, newItem: CheckOut): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: CheckOut, newItem: CheckOut): Boolean {
            return oldItem.id == newItem.id
        }
    }

    val differ = AsyncListDiffer(this, differCallback)

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