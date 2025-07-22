package com.example.fastdelivery.profile.domain.usecases

import com.example.fastdelivery.profile.data.local.UserDao
import com.example.fastdelivery.profile.data.local.UserEntity
import com.example.fastdelivery.profile.data.remote.UserApi
import com.example.fastdelivery.profile.domain.model.UserResponse
import io.mockk.*
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.runTest
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test
import retrofit2.Response

@ExperimentalCoroutinesApi
class GetUserByEmailUseCaseTest {

    private val userDao: UserDao = mockk()
    private val userApi: UserApi = mockk()
    private lateinit var useCase: GetUserByEmailUseCase

    @Before
    fun setup() {
        useCase = GetUserByEmailUseCase(userDao, userApi)
    }

    @Test
    fun `returns local user if found`() = runTest {
        val email = "local@example.com"
        val localUser = UserEntity("1", "Local User", email, "Country", null)

        coEvery { userDao.getUserByEmail(email) } returns localUser

        val result = useCase(email).first()
        assertTrue(result.isSuccess)
        assertEquals(localUser, result.getOrNull())

        coVerify(exactly = 1) { userDao.getUserByEmail(email) }
        coVerify(exactly = 0) { userApi.getUserByEmail(any()) }
    }

    @Test
    fun `fetches remote user if local user not found`() = runTest {
        val email = "remote@example.com"

        coEvery { userDao.getUserByEmail(email) } returns null

        val responseMock = mockk<Response<UserResponse>>()
        val remoteResponse = mockk<UserResponse>(relaxed = true)

        every { responseMock.isSuccessful } returns true
        every { responseMock.body() } returns remoteResponse

        coEvery { userApi.getUserByEmail(email) } returns responseMock

        every { remoteResponse.id } returns "2"
        every { remoteResponse.fullName } returns "Remote User"
        every { remoteResponse.email } returns email
        every { remoteResponse.nationality } returns "Country"
        every { remoteResponse.imageUrl } returns null

        coEvery { userDao.upsertUser(any()) } returns Unit

        val result = useCase(email).first()

        assertTrue(result.isSuccess)
        val savedUser = result.getOrNull()
        assertEquals("2", savedUser?.id)
        assertEquals("Remote User", savedUser?.fullName)
        assertEquals(email, savedUser?.email)
        assertEquals("Country", savedUser?.nationality)
        assertNull(savedUser?.imageUrl)

        coVerifyOrder {
            userDao.getUserByEmail(email)
            userApi.getUserByEmail(email)
            userDao.upsertUser(any())
        }
    }

    @Test
    fun `returns failure if remote fetch fails`() = runTest {
        val email = "fail@example.com"

        coEvery { userDao.getUserByEmail(email) } returns null

        val responseMock = mockk<Response<UserResponse>>()
        every { responseMock.isSuccessful } returns false
        every { responseMock.code() } returns 404

        coEvery { userApi.getUserByEmail(email) } returns responseMock

        val result = useCase(email).first()
        assertTrue(result.isFailure)
        assertTrue(result.exceptionOrNull()?.message?.contains("Error: 404") ?: false)

        coVerifyOrder {
            userDao.getUserByEmail(email)
            userApi.getUserByEmail(email)
        }
        coVerify(exactly = 0) { userDao.upsertUser(any()) }
    }
}
