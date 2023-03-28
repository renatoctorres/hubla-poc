package com.hubla.challenge.core.service

import com.hubla.challenge.core.model.objectmother.SellerObjectMother
import com.hubla.challenge.infra.persistence.entity.Seller
import com.hubla.challenge.infra.persistence.repository.SellerRepository
import io.mockk.every
import io.mockk.just
import io.mockk.mockk
import io.mockk.runs
import io.mockk.verify
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test
import java.util.*

internal class SellerServiceTest {

    private val repository: SellerRepository = mockk(relaxed = true)
    private val sellerAccountService: SellerAccountService = mockk(relaxed = true)
    private val service: SellerService = SellerService(
        repository = repository,
        sellerAccountService = sellerAccountService,
    )
    private val seller: Seller = SellerObjectMother.build()

    @Nested
    inner class WhenCreatingSeller {
        @Test
        fun `it should repository persist Seller`() {
            val expected: Seller = seller
            every { repository.saveAndFlush(any()) } returns expected
            val actual = service.save(seller)
            assertEquals(expected, actual)
            verify(exactly = 1) { repository.saveAndFlush(any()) }
        }
    }

    @Nested
    inner class WhenFindAll {
        @Test
        fun `Should repository find All Sellers`() {
            val expected: Seller = seller
            every { repository.findAll() } returns listOf(expected)
            val actual = service.findAll()[0]
            assertEquals(expected, actual)
            verify(exactly = 1) { service.findAll().size }
        }
    }

    @Nested
    inner class WhenFindByID {

        @Test
        fun `Should repository find an Seller by ID`() {
            val expected: Optional<Seller> = Optional.of(seller)
            every { repository.findById(any()) } returns expected
            val actual = service.findById(seller.id)
            assertEquals(expected, actual)
            verify(exactly = 1) { service.findById(seller.id) }
        }
    }

    @Nested
    inner class WhenDeletingById {
        @Test
        fun `should delete Seller`() {
            val expected: Optional<Seller> = Optional.of(seller)
            every { repository.findById(any()) } returns expected
            every { repository.deleteById(any()) } just runs
            service.deleteById(expected.get().id)
            verify(exactly = 1) { service.deleteById(any()) }
        }
    }
}
