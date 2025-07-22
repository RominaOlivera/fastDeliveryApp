package com.example.fastdelivery.order.di

import com.example.fastdelivery.order.domain.repository.OrderRepository
import com.example.fastdelivery.order.domain.usecases.GetOrdersForUserUseCase
import com.example.fastdelivery.order.domain.usecases.PlaceOrderUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent

@Module
@InstallIn(ViewModelComponent::class)
object OrderUseCaseModule {

    @Provides
    fun providePlaceOrderUseCase(repository: OrderRepository) = PlaceOrderUseCase(repository)

    @Provides
    fun provideGetOrdersForUserUseCase(repository: OrderRepository) = GetOrdersForUserUseCase(repository)
}
