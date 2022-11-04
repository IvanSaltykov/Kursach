package com.example.worldresort.ui.adapters.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.worldresort.databinding.ItemResortBookBinding
import com.example.worldresort.model.BookingDataUser
import com.example.worldresort.model.Resort
import com.example.worldresort.ui.adapters.viewholder.ResortBookRecyclerViewHolder

class ResortBookRecyclerViewAdapter(
    private val onItemClickListener: onClickListener
) : RecyclerView.Adapter<ResortBookRecyclerViewHolder>() {
    private var list = mutableListOf<Resort>()
    private var dataUsers = mutableListOf<BookingDataUser>()
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ResortBookRecyclerViewHolder {
        return ResortBookRecyclerViewHolder(
            ItemResortBookBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        )
    }

    override fun onBindViewHolder(holder: ResortBookRecyclerViewHolder, position: Int) {
        val item = list.get(position)
        val dataItem = dataUsers.get(position)
        holder.bind(item, dataItem, onItemClickListener)
    }

    override fun getItemCount() = list.size

    fun submitList(items: List<Resort>, dataItems: List<BookingDataUser>) {
        list.clear()
        list.addAll(items)
        dataUsers.clear()
        dataUsers.addAll(dataItems)
        notifyDataSetChanged()
    }
    fun addItem(item: Resort) {
        list.add(item)
        notifyItemInserted(list.lastIndex)
    }
    interface onClickListener {
        fun onClickItem(resort: Resort, dataUser: BookingDataUser)
    }
}