package com.example.kud.data.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.kud.R
import com.smarteist.autoimageslider.SliderViewAdapter

class AdapterBanner(imageUrl: ArrayList<String>) :
    SliderViewAdapter<AdapterBanner.SliderViewHolder>() {

    private var sliderList: ArrayList<String> = imageUrl

    override fun getCount(): Int {

        return sliderList.size
    }

    override fun onCreateViewHolder(parent: ViewGroup?): SliderViewHolder {
        return SliderViewHolder(LayoutInflater.from(parent!!.context).inflate(R.layout.item_banner, null))
    }


    override fun onBindViewHolder(viewHolder: SliderViewHolder?, position: Int) {

        if (viewHolder != null) {

            Glide.with(viewHolder.itemView).load(sliderList[position]).fitCenter()
                .into(viewHolder.imageView)
        }
    }


    class SliderViewHolder(itemView: View?) : ViewHolder(itemView) {

        var imageView: ImageView = itemView!!.findViewById(R.id.iv_auto_image_slider)
    }
}