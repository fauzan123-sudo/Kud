package com.example.kud.data.adapter

import androidx.recyclerview.widget.RecyclerView

class CustomAdapter
//class ListAdapter(
//    private val clickListener: ListClickListener
//) : RecyclerView.Adapter<ListAdapter.MyViewHolder>() {
//
//    private var itemsList = emptyList<Item>()
//    private lateinit var item: Item
//
//    class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
//
//    }
//
//    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
//        return MyViewHolder(
//            LayoutInflater.from(parent.context).inflate(R.layout.item_row_layout, parent, false)
//        )
//    }
//
//    override fun getItemCount(): Int {
//        return itemsList.size
//    }
//
//    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
//        item = itemsList.get(position)
//        //always remember this technique to save the values in val type
//        val currentItem = itemsList[position]
//        holder.itemView.itemNameTV.text = currentItem.itemName.toString()
//        holder.itemView.itemCodeTV.text = currentItem.itemCode.toString()
//        holder.itemView.itemCategoryTV.text = currentItem.itemCategory.toString()
//        holder.itemView.itemDescriptionTV.text = currentItem.itemDescription.toString()
//        holder.itemView.itemSellingPriceTV.text = currentItem.itemSellingPrice.toString()
//        holder.itemView.itemStockTV.text = currentItem.itemStock.toString()
//        holder.itemView.deleteItem.setOnClickListener {
//            val itName = holder.itemView.itemNameTV.text.toString()
//            val itCode = holder.itemView.itemCodeTV.text.toString()
//            val itCategory = holder.itemView.itemCategoryTV.text.toString()
//            val itDescription = holder.itemView.itemDescriptionTV.text.toString()
//            val itSellingPrice = holder.itemView.itemSellingPriceTV.text.toString()
//            val itStock = holder.itemView.itemStockTV.text.toString()
//            val itime = Item(0, itName, itCode, itCategory, itSellingPrice, itStock, itDescription)
//            // Call the click listener
//            clickListener.onClick(iitem)
//        }
//    }
//
//    fun setData(item: List<Item>) {
//        this.itemsList = item
//        notifyDataSetChanged()
//
//    }
//}
//
//// Click listener class
//class ListClickListener(val clickListener: (item: Item) -> Unit) {
//    fun onClick(item: Item) = clickListener(item)
//}