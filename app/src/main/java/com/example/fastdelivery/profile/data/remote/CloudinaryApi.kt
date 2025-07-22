package com.example.fastdelivery.profile.data.remote

import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.Part

interface CloudinaryApi {

    @Multipart
    @POST("image/upload")
    suspend fun uploadImage(
        @Part file: MultipartBody.Part,
        @Part("upload_preset") uploadPreset: RequestBody
    ): CloudinaryResponse

    companion object {
        fun create(cloudName: String): CloudinaryApi {
            val retrofit = Retrofit.Builder()
                .baseUrl("https://api.cloudinary.com/v1_1/$cloudName/")
                .addConverterFactory(GsonConverterFactory.create())
                .build()

            return retrofit.create(CloudinaryApi::class.java)
        }
    }
}
