package com.example.kud.data.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import androidx.navigation.findNavController
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.example.kud.R
import com.example.kud.data.model.User
import com.example.kud.databinding.ItemUserBinding
import com.example.kud.ui.fragment.home.BerandaFragmentDirections

class UserAdapter : RecyclerView.Adapter<UserAdapter.ViewHolder>() {

    class ViewHolder(val binding: ItemUserBinding) : RecyclerView.ViewHolder(binding.root)

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
        ItemUserBinding.inflate(LayoutInflater.from(parent.context), parent, false)
    )

    override fun getItemCount() = differ.currentList.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val dataPosition = differ.currentList[position]
        with(holder) {
            with(binding) {
                firstName.text = dataPosition.firstName
                middleName.text = dataPosition.middleName
                lastName.text = dataPosition.lastName
                imgUser.load(dataPosition.image)

                rowLayout.startAnimation(
                    AnimationUtils.loadAnimation(
                        rowLayout.context,
                        R.anim.slide_in
                    )
                )

                rowLayout.startAnimation(
                    AnimationUtils.loadAnimation(
                        rowLayout.context,
                        R.anim.slide_in
                    )
                )

                rowLayout.setOnClickListener {
                    val action =
                        BerandaFragmentDirections.actionBerandaFragmentToUpdateFragment(dataPosition)
                    holder.itemView.findNavController().navigate(action)
                }
            }
        }
    }
}

