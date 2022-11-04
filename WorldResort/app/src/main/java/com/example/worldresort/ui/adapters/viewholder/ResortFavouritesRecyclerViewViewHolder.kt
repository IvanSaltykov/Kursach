package com.example.worldresort.ui.adapters.viewholder

import android.annotation.SuppressLint
import androidx.recyclerview.widget.RecyclerView
import com.example.worldresort.databinding.ItemResortFavouritesBinding
import com.example.worldresort.model.Resort
import com.example.worldresort.ui.interfaces.OnItemClickListener

class ResortFavouritesRecyclerViewViewHolder(private val binding: ItemResortFavouritesBinding) : RecyclerView.ViewHolder(binding.root) {
    @SuppressLint("SetTextI18n")
    fun bind(resort: Resort, clickListener: OnItemClickListener) = with(binding) {
        textViewName.text = resort.hotel
        textViewDiscration.text = "${resort.world}, ${resort.country}, ${resort.city}"
        imageView.setOnClickListener {
            clickListener.onClickImageItem(resort)
        }
        root.setOnClickListener {
            clickListener.onClickItem(resort)
        }
        root.setOnLongClickListener {
            return@setOnLongClickListener true
        }
    }
}