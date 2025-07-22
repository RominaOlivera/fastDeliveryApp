package com.example.fastdelivery.cart.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.fastdelivery.cart.data.local.entities.CartItemEntity
import com.example.fastdelivery.order.data.local.LocalOrderDao
import com.example.fastdelivery.order.data.local.OrderItemListConverter
import com.example.fastdelivery.order.data.local.LocalOrderEntity
import com.example.fastdelivery.profile.data.local.UserDao
import com.example.fastdelivery.profile.data.local.UserEntity

@Database(
    entities = [CartItemEntity::class, LocalOrderEntity::class, UserEntity::class],
    version = 4,
    exportSchema = false
)
@TypeConverters(OrderItemListConverter::class)
abstract class AppDatabase : RoomDatabase() {
    abstract fun cartItemDao(): CartItemDao
    abstract fun localOrderDao(): LocalOrderDao
    abstract fun userDao(): UserDao
}
