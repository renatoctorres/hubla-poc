package com.hubla.challenge.core.service

import com.hubla.challenge.core.model.objectmother.SellerAccountObjectMother
import com.hubla.challenge.infra.persistence.entity.SellerAccount
import com.hubla.challenge.infra.persistence.repository.SellerAccountRepository
import io.mockk.every
import io.mockk.just
import io.mockk.mockk
import io.mockk.runs
import io.mockk.verify
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test
import java.util.*

internal class SellerAccountServiceTest {
    private val repository: SellerAccountRepository = mockk(relaxed = true)
    private val service: SellerAccountService = SellerAccountService(
        repository = repository,
    )

    private val sellerAccount: SellerAccount = SellerAccountObjectMother.build()

    @Nested
    inner class WhenCreatingSellerAccount {
        @Test
        fun `Should persist Sellers Account`() {
            val expected: SellerAccount = sellerAccount
            every { repository.saveAndFlush(any()) } returns expected
            val actual = service.save(expected)
            assertEquals(expected, actual)
            verify(exactly = 1) { repository.saveAndFlush(any()) }
        }
    }

    @Nested
    inner class WhenFindAll {
        @Test
        fun `Should find All Sellers Account`() {
            val expected: SellerAccount = sellerAccount
            every { repository.findAll() } returns listOf(expected)
            val actual = service.findAll()[0]
            assertEquals(expected, actual)
            verify(exactly = 1) { service.findAll().size }
        }
    }

    @Nested
    inner class WhenFindByID {

        @Test
        fun `Should find an Seller Account by ID`() {
            val expected: Optional<SellerAccount> = Optional.of(sellerAccount)
            every { repository.findById(any()) } returns expected
            val actual = service.findById(sellerAccount.id)
            assertEquals(expected, actual)
            verify(exactly = 1) { service.findById(sellerAccount.id) }
        }
    }

    @Nested
    inner class WhenDeletingById {
        @Test
        fun `should delete Seller Account`() {
            val expected: Optional<SellerAccount> = Optional.of(sellerAccount)
            every { repository.findById(any()) } returns expected
            every { repository.deleteById(any()) } just runs
            service.deleteById(expected.get().id)
            verify(exactly = 1) { service.deleteById(any()) }
        }
    }
}
