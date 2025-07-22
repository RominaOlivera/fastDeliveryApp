package com.example.fastdelivery.profile.domain.usecases

import android.content.ContentResolver
import android.net.Uri
import com.example.fastdelivery.profile.data.remote.CloudinaryApi
import com.example.fastdelivery.profile.data.remote.CloudinaryResponse
import io.mockk.*
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test
import java.io.ByteArrayInputStream
import kotlin.test.assertEquals
import kotlin.test.assertTrue

@ExperimentalCoroutinesApi
class UploadUserImageUseCaseTest {

    private lateinit var contentResolver: ContentResolver
    private lateinit var imageUri: Uri
    private lateinit var cloudinaryApi: CloudinaryApi
    private lateinit var useCase: UploadUserImageUseCase

    private val cloudName = "dgjooxflt"

    @Before
    fun setup() {
        contentResolver = mockk()
        imageUri = mockk()
        cloudinaryApi = mockk()

        val factory = mockk<(String) -> CloudinaryApi>()
        every { factory.invoke(cloudName) } returns cloudinaryApi

        useCase = UploadUserImageUseCase(factory)
    }

    @Test
    fun `invoke uploads image and returns secure_url on success`() = runTest {

        val fakeImageBytes = "fake image data".toByteArray()
        val fakeInputStream = ByteArrayInputStream(fakeImageBytes)
        every { contentResolver.openInputStream(imageUri) } returns fakeInputStream

        val expectedUrl = "https://cloudinary.com/fakeimage.jpg"
        val fakeResponse = CloudinaryResponse(secure_url = expectedUrl)


        coEvery { cloudinaryApi.uploadImage(any(), any()) } returns fakeResponse

        val result = useCase.invoke(contentResolver, imageUri)

        assertTrue(result.isSuccess)
        assertEquals(expectedUrl, result.getOrNull())

        verify { contentResolver.openInputStream(imageUri) }
        coVerify { cloudinaryApi.uploadImage(any(), any()) }
    }

    @Test
    fun `invoke returns failure if openInputStream throws exception`() = runTest {
        every { contentResolver.openInputStream(imageUri) } throws RuntimeException("Stream error")

        val result = useCase.invoke(contentResolver, imageUri)

        assertTrue(result.isFailure)
        assertEquals("Stream error", result.exceptionOrNull()?.message)
    }
}
