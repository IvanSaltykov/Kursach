package com.example.worldresort.model

data class InsertResort(
    val world: String,
    val country: String,
    val city: String,
    val hotel: String,
    val imageHotel: Int,
    val imageResort: Int,
    val price: Int,
    val description: String
)
