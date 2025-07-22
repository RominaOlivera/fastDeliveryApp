package com.example.fastdelivery.cart.di

import com.example.fastdelivery.cart.domain.repository.CartRepository
import com.example.fastdelivery.cart.domain.usecases.AddCartItemUseCase
import com.example.fastdelivery.cart.domain.usecases.ClearCartUseCase
import com.example.fastdelivery.cart.domain.usecases.DecreaseCartItemUseCase
import com.example.fastdelivery.cart.domain.usecases.IncreaseCartItemUseCase
import com.example.fastdelivery.cart.domain.usecases.RemoveCartItemUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent

@Module
@InstallIn(ViewModelComponent::class)
object CartUseCaseModule {

    @Provides
    fun provideAddCartItemUseCase(repository: CartRepository) = AddCartItemUseCase(repository)

    @Provides
    fun provideRemoveCartItemUseCase(repository: CartRepository) = RemoveCartItemUseCase(repository)

    @Provides
    fun provideClearCartUseCase(repository: CartRepository) = ClearCartUseCase(repository)

    @Provides
    fun provideIncreaseCartItemUseCase(repository: CartRepository) = IncreaseCartItemUseCase(repository)

    @Provides
    fun provideDecreaseCartItemUseCase(repository: CartRepository) = DecreaseCartItemUseCase(repository)
}
