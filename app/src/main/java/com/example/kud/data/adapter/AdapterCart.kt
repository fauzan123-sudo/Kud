package com.example.kud.data.adapter

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.example.kud.data.model.cart.list.DataX
import com.example.kud.databinding.ItemCheckoutWithCheckboxBinding
import com.example.kud.utils.Constants.IMAGE_OBAT
import com.example.kud.utils.Helper

class AdapterCart(
    val context: Context,
//    private val onItemClickListener: (DataX) -> Unit
) : RecyclerView.Adapter<AdapterCart.ViewHolder>() {
    var itemClickListener: ItemClickListener? = null

    inner class ViewHolder(val binding: ItemCheckoutWithCheckboxBinding) :
        RecyclerView.ViewHolder(binding.root)


    private val differCallback = object : DiffUtil.ItemCallback<DataX>() {
        override fun areItemsTheSame(
            oldItem: DataX,
            newItem: DataX
        ): Boolean {
            return oldItem == newItem
        }

        override fun areContentsTheSame(
            oldItem: DataX,
            newItem: DataX
        ): Boolean {
            return oldItem == newItem
        }
    }

    val differ = AsyncListDiffer(this, differCallback)

    interface ItemClickListener {
        fun upsert(drugId: Int?, status: Int, qty: Int)
        fun onDeleteButtonClicked(position: Int, drugId: Int)
        fun checkBox(position: Int, cartId: String, data: DataX)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = ViewHolder(
        ItemCheckoutWithCheckboxBinding.inflate(LayoutInflater.from(parent.context), parent, false)
    )

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val myPosition = differ.currentList[position]
        var amountItem = myPosition.qty.toInt()
        with(holder) {
            binding.imageProduct.load(IMAGE_OBAT + myPosition.image)
            binding.drugName.text = myPosition.nama_obat
            binding.drugType.text = myPosition.kategori
            binding.drugPrice.text = Helper().gantiRupiah(myPosition.harga)
            binding.totalItems.text = myPosition.qty

            binding.checkBox.setOnCheckedChangeListener { _, isChecked ->
                myPosition.isSelected = isChecked
                val selectedItems = differ.currentList.filter { it.isSelected }
                val cartId = selectedItems.joinToString(", ") { it.id_keranjang.toString() }
                itemClickListener?.checkBox(layoutPosition, cartId, myPosition)
//                Log.d("CheckBox adapter", "Item ${myPosition} dicentang")

            }

            binding.btnPlus.setOnClickListener {
                amountItem++
                binding.totalItems.text = amountItem.toString()
                Log.d("current qty", "$amountItem")
                itemClickListener?.upsert(myPosition.id_obat, 1, amountItem)
            }

            binding.btnMinus.setOnClickListener {
                amountItem--
                binding.totalItems.text = amountItem.toString()
                Log.d("current qty", "$amountItem")
                itemClickListener?.upsert(myPosition.id_obat, 2, amountItem)
            }

            binding.deleteItem.setOnClickListener {
                itemClickListener?.onDeleteButtonClicked(layoutPosition, myPosition.id_obat)
            }
        }
    }

    fun deleteItem(position: Int) {
        val newList = differ.currentList.toMutableList()
        newList.removeAt(position)
        differ.submitList(newList)
    }

    override fun getItemCount() = differ.currentList.size


}