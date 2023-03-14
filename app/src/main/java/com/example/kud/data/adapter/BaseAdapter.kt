package com.example.kud.data.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView

abstract class BaseAdapter<T>(
    private val data:List<T>,
    private val layoutId:Int
) : RecyclerView.Adapter<BaseAdapter<T>.BaseViewHolder>() {
    inner class BaseViewHolder(val view:View):RecyclerView.ViewHolder(view) {
        fun bind(item:T){
            setDataToView(view, item)
        }

    }

    private fun setDataToView(view: View, item: T) {

    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder {
      return BaseViewHolder(LayoutInflater.from(parent.context).inflate(layoutId, parent,false))
    }

    override fun onBindViewHolder(holder: BaseViewHolder, position: Int) {
        holder.bind(data[position])
    }

    override fun getItemCount(): Int {
        return data.size
    }
}