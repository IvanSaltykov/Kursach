package com.example.worldresort.ui.adapters.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.worldresort.databinding.ItemResortBinding
import com.example.worldresort.model.Resort
import com.example.worldresort.ui.adapters.viewholder.ResortRecyclerViewHolder
import com.example.worldresort.ui.interfaces.OnItemClickListener

class ResortRecyclerViewAdapter(
    private val itemClickListener: OnItemClickListener
) : RecyclerView.Adapter<ResortRecyclerViewHolder>() {
    private var list = mutableListOf<Resort>()
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ResortRecyclerViewHolder {
        return ResortRecyclerViewHolder(
            ItemResortBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        )
    }

    override fun onBindViewHolder(holder: ResortRecyclerViewHolder, position: Int) {
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