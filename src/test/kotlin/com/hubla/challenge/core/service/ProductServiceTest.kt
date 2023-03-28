package com.hubla.challenge.core.service

import com.hubla.challenge.core.model.objectmother.ProductObjectMother
import com.hubla.challenge.infra.persistence.entity.Product
import com.hubla.challenge.infra.persistence.repository.ProductRepository
import io.mockk.every
import io.mockk.just
import io.mockk.mockk
import io.mockk.runs
import io.mockk.verify
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test
import java.util.*

internal class ProductServiceTest {
    private val repository: ProductRepository = mockk(relaxed = true)
    private val service: ProductService = ProductService(repository = repository)
    private val product: Product = ProductObjectMother.build()

    @Nested
    inner class WhenCreatingProduct {
        @Test
        fun `it should repository persist Product`() {
            val expected: Product = product
            every { repository.saveAndFlush(any()) } returns expected
            val actual = service.save(product)
            assertEquals(expected, actual)
            verify(exactly = 1) { repository.saveAndFlush(any()) }
        }
    }

    @Nested
    inner class WhenFindAll {
        @Test
        fun `Should repository find All Products`() {
            val expected: Product = product
            every { repository.findAll() } returns listOf(expected)
            val actual = service.findAll()[0]
            assertEquals(expected, actual)
            verify(exactly = 1) { service.findAll().size }
        }
    }

    @Nested
    inner class WhenFindByID {

        @Test
        fun `Should repository find an Product by ID`() {
            val expected: Optional<Product> = Optional.of(product)
            every { repository.findById(any()) } returns expected
            val actual = service.findById(product.id)
            assertEquals(expected, actual)
            verify(exactly = 1) { service.findById(product.id) }
        }
    }

    @Nested
    inner class WhenDeletingById {
        @Test
        fun `should delete Product`() {
            val expected: Optional<Product> = Optional.of(product)
            every { repository.findById(any()) } returns expected
            every { repository.deleteById(any()) } just runs
            service.deleteById(expected.get().id)
            verify(exactly = 1) { service.deleteById(any()) }
        }
    }
}
