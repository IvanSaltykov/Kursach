package com.example.worldresort.model

data class Resort(
    val id: Int,
    val world: String,
    val country: String,
    val city: String,
    val hotel: String,
    val image: Int,
    val idSpecial: Int? = null,
    val price: Int,
    val description: String
)
