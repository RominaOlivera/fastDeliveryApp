package com.example.fastdelivery.di

import android.content.Context
import androidx.room.Room
import com.example.fastdelivery.cart.data.local.AppDatabase
import com.example.fastdelivery.cart.data.local.CartItemDao
import com.example.fastdelivery.order.data.local.LocalOrderDao
import com.example.fastdelivery.profile.data.local.UserDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Provides
    @Singleton
    fun provideDatabase(@ApplicationContext context: Context): AppDatabase =
        Room.databaseBuilder(
            context,
            AppDatabase::class.java,
            "fastdelivery_database"
        ).fallbackToDestructiveMigration()
            .build()

    @Provides
    fun provideCartItemDao(db: AppDatabase): CartItemDao = db.cartItemDao()

    @Provides
    fun provideLocalOrderDao(db: AppDatabase): LocalOrderDao = db.localOrderDao()

    @Provides
    fun provideUserDao(db: AppDatabase): UserDao = db.userDao()
}
