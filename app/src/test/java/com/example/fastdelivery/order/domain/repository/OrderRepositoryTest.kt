import com.example.fastdelivery.order.data.local.LocalOrderDao
import com.example.fastdelivery.order.data.local.LocalOrderEntity
import com.example.fastdelivery.order.data.remote.OrderApi
import com.example.fastdelivery.order.domain.model.OrderItem
import com.example.fastdelivery.order.domain.repository.OrderRepository
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runTest
import org.junit.Test
import org.junit.Before
import io.mockk.coEvery
import io.mockk.mockk
import org.junit.Assert.assertEquals

class OrderRepositoryTest {

    private lateinit var orderRepository: OrderRepository
    private val orderApi = mockk<OrderApi>(relaxed = true)
    private val localOrderDao = mockk<LocalOrderDao>(relaxed = true)

    @Before
    fun setup() {
        orderRepository = OrderRepository(orderApi, localOrderDao)
    }

    @Test
    fun `getLocalOrders returns expected orders`() = runTest {
        val userId = "user123"
        val productList = listOf(
            OrderItem(
                name = "Pizza fugazzeta",
                description = "Pizza with caramelized onions and special seasonings",
                imageUrl = "https://resizer.glanacion.com/resizer/v2/-VTRMIWQDRFAVPKCEUV6RGFI7IU.jpg?auth=8740f1510c1da057ccfdef522340469348389532f238823f107f349895146231&width=880&height=586&quality=70&smart=true",
                price = 400.0,
                hasDrink = false,
                quantity = 1
            ),
            OrderItem(
                name = "Coca Cola",
                description = "fizzy drink",
                imageUrl = "https://example.com/cocacola.png",
                price = 150.0,
                hasDrink = true,
                quantity = 2
            )
        )

        val order = LocalOrderEntity(
            orderId = "order123",
            userId = userId,
            productIds = productList,
            total = productList.sumOf { it.price * it.quantity },
            timestamp = System.currentTimeMillis()
        )

        val flowMock = flowOf(listOf(order))

        coEvery { localOrderDao.getOrdersForUser(userId) } returns flowMock

        val resultFlow = orderRepository.getLocalOrders(userId)

        resultFlow.collect { orders ->
            assertEquals(1, orders.size)
            assertEquals(order, orders[0])
        }
    }
}
