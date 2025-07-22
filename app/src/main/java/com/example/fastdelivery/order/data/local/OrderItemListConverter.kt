package com.example.fastdelivery.order.data.local

import androidx.room.TypeConverter
import com.example.fastdelivery.order.domain.model.OrderItem
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class OrderItemListConverter {
    @TypeConverter
    fun fromList(value: List<OrderItem>): String {
        return Gson().toJson(value)
    }

    @TypeConverter
    fun toList(value: String): List<OrderItem> {
        val type = object : TypeToken<List<OrderItem>>() {}.type
        return Gson().fromJson(value, type)
    }
}
