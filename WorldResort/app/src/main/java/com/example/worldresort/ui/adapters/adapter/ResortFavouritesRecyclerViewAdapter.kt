package com.example.worldresort.ui.adapters.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.worldresort.databinding.ItemResortFavouritesBinding
import com.example.worldresort.model.Resort
import com.example.worldresort.ui.adapters.viewholder.ResortFavouritesRecyclerViewViewHolder
import com.example.worldresort.ui.interfaces.OnItemClickListener

class ResortFavouritesRecyclerViewAdapter(
    private val itemClickListener: OnItemClickListener
) : RecyclerView.Adapter<ResortFavouritesRecyclerViewViewHolder>() {
    private var list = mutableListOf<Resort>()
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int,
    ): ResortFavouritesRecyclerViewViewHolder {
        return ResortFavouritesRecyclerViewViewHolder(
            ItemResortFavouritesBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        )
    }

    override fun onBindViewHolder(holder: ResortFavouritesRecyclerViewViewHolder, position: Int) {
        val item = list.get(position)
        holder.bind(item, itemClickListener)
    }

    override fun getItemCount() = list.size

    fun submitList(days: List<Resort>) {
        list.clear()
        list.addAll(days)
        notifyDataSetChanged()
    }
    fun addItem(item: Resort) {
        list.add(item)
        notifyItemInserted(list.lastIndex)
    }
}