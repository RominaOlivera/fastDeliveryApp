package com.example.fastdelivery.profile.di

import com.example.fastdelivery.profile.data.remote.CloudinaryApi
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent


@Module
@InstallIn(SingletonComponent::class)
object CloudinaryModule {

    @Provides
    fun provideCloudinaryApiFactory(): @JvmSuppressWildcards (String) -> CloudinaryApi = { cloudName ->
        CloudinaryApi.create(cloudName)
    }
}
