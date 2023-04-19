package com.example.kud.data.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import androidx.viewbinding.ViewBinding


//abstract class BasesAdapter<T, VH : BasesAdapter.ViewHolder<T>>(
//    private val context: Context,
//    private val layoutResId: Int,
//    private val items: List<T>
//) : RecyclerView.Adapter<VH>(){
//
//}
//abstract class BasesAdapter<T, VB : ViewBinding, VH : BaseAdapter.BasesViewHolder<T, VB>>(
//    var data: MutableList<T>
//) : RecyclerView.Adapter<VH>() {
//
//    abstract fun createBinding(parent: ViewGroup): VB
//
//    abstract fun createViewHolder(binding: VB): VH
//
//    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VH {
//        val binding = createBinding(parent)
//        return createViewHolder(binding)
//    }
//
//    override fun getItemCount(): Int = data.size
//
//    override fun onBindViewHolder(holder: VH, position: Int) {
//        holder.bind(data[position])
//    }
//
//    fun updateData(newData: List<T>) {
//        data.clear()
//        data.addAll(newData)
//        notifyDataSetChanged()
//    }
//
//    abstract class BaseViewHolder<T, VB : ViewBinding>(val binding: VB) :
//        RecyclerView.ViewHolder(binding.root) {
//
//        abstract fun bind(item: T)
//    }
//}

//class MyAdapter(private val data: List<String>, private val listener: OnItemClickListener) :
//    RecyclerView.Adapter<MyAdapter.MyViewHolder>() {
//
//    interface OnItemClickListener {
//        fun onItemClick(item: String)
//    }
//
//    inner class MyViewHolder(private val binding: ItemLayoutBinding) :
//        RecyclerView.ViewHolder(binding.root) {
//        fun bind(item: String) {
//            binding.apply {
//                textView.text = item
//                root.setOnClickListener {
//                    listener.onItemClick(item)
//                }
//            }
//        }
//    }
//
//    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
//        val binding = ItemLayoutBinding.inflate(LayoutInflater.from(parent.context), parent, false)
//        return MyViewHolder(binding)
//    }
//
//    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
//        holder.bind(data[position])
//    }
//
//    override fun getItemCount() = data.size
//}




