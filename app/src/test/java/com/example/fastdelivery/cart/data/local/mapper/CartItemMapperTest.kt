package com.example.fastdelivery.cart.data.local.mapper

import com.example.fastdelivery.cart.data.local.entities.CartItemEntity
import com.example.fastdelivery.cart.domain.model.CartItem
import org.junit.Assert.assertEquals
import org.junit.Test

class CartItemMapperTest {

    @Test
    fun `toModel maps entity to model correctly`() {
        val entity = CartItemEntity("1", "Test Product", 9.99, 2, "image.png")

        val model = entity.toModel()

        assertEquals("1", model.id)
        assertEquals("Test Product", model.name)
        assertEquals(9.99, model.price, 0.001)
        assertEquals(2, model.quantity)
        assertEquals("image.png", model.image)
        assertEquals("", model.description)
        assertEquals("image.png", model.imageUrl)
        assertEquals(false, model.hasDrink)
    }

    @Test
    fun `toEntity maps model to entity correctly`() {
        val model = CartItem(
            id = "1",
            name = "Test Product",
            price = 9.99,
            quantity = 2,
            image = "image.png",
            description = "",
            imageUrl = "image.png",
            hasDrink = false
        )

        val entity = model.toEntity()

        assertEquals("1", entity.productId)
        assertEquals("Test Product", entity.name)
        assertEquals(9.99, entity.price, 0.001)
        assertEquals(2, entity.quantity)
        assertEquals("image.png", entity.image)
    }
}
