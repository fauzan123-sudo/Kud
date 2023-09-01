package com.example.kud.data.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.kud.R
import com.example.kud.data.model.FakeApiItem


//class FakeAdapter : RecyclerView.Adapter<FakeAdapter.ViewHolder>() {
//
//    inner class ViewHolder(val binding: LayoutFakeBinding) : RecyclerView.ViewHolder(binding.root)
//
//    private val differCallback = object : DiffUtil.ItemCallback<FakeApiItem>() {
//        override fun areItemsTheSame(oldItem: FakeApiItem, newItem: FakeApiItem): Boolean {
//            return oldItem == newItem
//        }
//
//        override fun areContentsTheSame(oldItem: FakeApiItem, newItem: FakeApiItem): Boolean {
//            return oldItem == newItem
//        }
//    }
//
//    val differ = AsyncListDiffer(this, differCallback)
//
//
//    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = ViewHolder(
//        LayoutFakeBinding.inflate(LayoutInflater.from(parent.context), parent, false)
//    )
//
//    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
//        val dataPosition = differ.currentList[position]
//
//        holder.binding.txtIdUser.text = dataPosition.userId.toString()
//        holder.binding.txtId.text = dataPosition.id.toString()
//        holder.binding.txtBody.text = dataPosition.body
//        holder.binding.txtTitle.text = dataPosition.title
//
//    }
//
//    override fun getItemCount(): Int {
//        return differ.currentList.size
//    }
//}