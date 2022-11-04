package com.example.worldresort.ui.interfaces

import com.example.worldresort.model.Resort

interface OnItemClickListener {
    fun onClickItem(resort: Resort)
    fun onClickImageItem(resort: Resort)
}