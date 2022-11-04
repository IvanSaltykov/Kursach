package com.example.worldresort.data

import androidx.core.graphics.red
import com.example.worldresort.R
import com.example.worldresort.model.InsertResort

class Resorts {
    fun dataResortList() : List<InsertResort> {
        return resorts()
    }
    private fun resorts() : List<InsertResort> {
        val resort = listOf(
            InsertResort(worlds[0], countries[0], cities[0], "Меридиан", R.drawable.meridian, 10000),
            InsertResort(worlds[0], countries[0], cities[0], "Mercure", R.drawable.mercure, 20560),
            InsertResort(worlds[1], countries[1], cities[1], "Radisson Hotel Agra", R.drawable.radisson_hotel_agra, 50200),
            InsertResort(worlds[2], countries[2], cities[2], "Lagoonie Lodge & Beach", R.drawable.lagoonie, 70000),
            InsertResort(worlds[3], countries[3], cities[3], "Hyatt Regency Mexico City", R.drawable.hyatt_regency, 40000),
            InsertResort(worlds[0], countries[0], cities[4], "AZIMUT Сити Отель Олимпик Москва", R.drawable.azimut, 32000)
        )
        return resort
    }
    val worlds = listOf(
        "Европа",
        "Азия",
        "Африка",
        "Америка"
    )
    val countries = listOf(
        "Россия",
        "Индия",
        "Египет",
        "США"
    )
    val cities = listOf(
        "Саранск",
        "Агра",
        "Сафага",
        "Мехико",
        "Москва"
    )
}