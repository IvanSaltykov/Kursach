package com.example.worldresort.ui.adapters.viewholder

import androidx.recyclerview.widget.RecyclerView
import com.example.worldresort.databinding.ItemResortBookBinding
import com.example.worldresort.model.BookingDataUser
import com.example.worldresort.model.Resort
import com.example.worldresort.ui.adapters.adapter.ResortBookRecyclerViewAdapter

class ResortBookRecyclerViewHolder(private val binding: ItemResortBookBinding) : RecyclerView.ViewHolder(binding.root) {
    fun bind(resort: Resort, dataUser: BookingDataUser, clickListener: ResortBookRecyclerViewAdapter.onClickListener) = with(binding) {
        textViewName.text = resort.hotel
        textViewDiscration.text = "${resort.world}, ${resort.country}, ${resort.city}"
        root.setOnClickListener {
            clickListener.onClickItem(resort, dataUser)
        }
        root.setOnLongClickListener {
            return@setOnLongClickListener true
        }
    }
}