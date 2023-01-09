package com.example.kud.data.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import androidx.navigation.findNavController
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.example.kud.R
import com.example.kud.data.model.User
import com.example.kud.ui.fragment.BerandaFragmentDirections
import kotlinx.android.synthetic.main.item_user.view.*

class UserAdapter : RecyclerView.Adapter<UserAdapter.ViewHolder>() {

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)

    private val differCallback = object : DiffUtil.ItemCallback<User>() {
        override fun areItemsTheSame(oldItem: User, newItem: User): Boolean {
            return oldItem == newItem
        }

        override fun areContentsTheSame(oldItem: User, newItem: User): Boolean {
            return oldItem == newItem
        }
    }

    val differ = AsyncListDiffer(this, differCallback)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = ViewHolder(
        LayoutInflater.from(parent.context).inflate(R.layout.item_user, parent, false)
    )

    override fun getItemCount() = differ.currentList.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val dataPosition = differ.currentList[position]

        holder.itemView.firstName.text = dataPosition.firstName
        holder.itemView.middleName.text = dataPosition.middleName
        holder.itemView.lastName.text = dataPosition.lastName
        holder.itemView.imgUser.load(dataPosition.image)

        holder.itemView.row_layout.startAnimation(AnimationUtils.loadAnimation(holder.itemView.row_layout.context, R.anim.slide_in))

        holder.itemView.row_layout.setOnClickListener {
            val action =
                BerandaFragmentDirections.actionBerandaFragmentToUpdateFragment(dataPosition)
            holder.itemView.findNavController().navigate(action)
        }
    }
}

