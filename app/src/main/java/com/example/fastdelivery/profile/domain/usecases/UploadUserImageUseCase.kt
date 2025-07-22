package com.example.fastdelivery.profile.domain.usecases

import android.content.ContentResolver
import android.net.Uri
import com.example.fastdelivery.profile.data.remote.CloudinaryApi
import kotlin.jvm.JvmSuppressWildcards
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.toRequestBody
import javax.inject.Inject

class UploadUserImageUseCase @Inject constructor(
    private val cloudinaryApiFactory: @JvmSuppressWildcards (String) -> CloudinaryApi
) {
    suspend operator fun invoke(contentResolver: ContentResolver, imageUri: Uri): Result<String> =
        withContext(Dispatchers.IO) {
            try {
                val inputStream = contentResolver.openInputStream(imageUri)!!
                val requestBody = inputStream.readBytes()
                    .toRequestBody("image/*".toMediaTypeOrNull())
                val multipartBody = MultipartBody.Part.createFormData(
                    "file", "profile.jpg", requestBody
                )

                val cloudName = "dgjooxflt"
                val uploadPreset = "fast_delivery_preset"
                val api = cloudinaryApiFactory(cloudName)
                val response = api.uploadImage(
                    multipartBody,
                    uploadPreset.toRequestBody("text/plain".toMediaTypeOrNull())
                )
                Result.success(response.secure_url)
            } catch (e: Exception) {
                Result.failure(e)
            }
        }
}
