package com.hubla.challenge.core.service

import com.hubla.challenge.core.model.objectmother.TransactionImportedObjectMother
import com.hubla.challenge.infra.persistence.entity.TransactionImported
import com.hubla.challenge.infra.persistence.repository.TransactionImportedRepository
import io.mockk.every
import io.mockk.just
import io.mockk.mockk
import io.mockk.runs
import io.mockk.verify
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test
import java.util.*

internal class TransactionServiceTest {
    private val repository: TransactionImportedRepository = mockk(relaxed = true)
    private val accountService: SellerAccountService = mockk(relaxed = true)
    private val service: TransactionService = TransactionService(
        repository = repository,
        accountService = accountService,
    )
    private val transaction: TransactionImported = TransactionImportedObjectMother.build()

    @Nested
    inner class WhenCreatingTransaction {
        @Test
        fun `it should repository persist Transaction`() {
            val expected: TransactionImported = transaction
            every { repository.saveAndFlush(any()) } returns expected
            val actual = service.save(transaction)
            Assertions.assertEquals(expected, actual)
            verify(exactly = 1) { repository.saveAndFlush(any()) }
        }
    }

    @Nested
    inner class WhenFindAll {
        @Test
        fun `Should repository find All Transactions`() {
            val expected: TransactionImported = transaction
            every { repository.findAll() } returns listOf(expected)
            val actual = service.findAll()[0]
            Assertions.assertEquals(expected, actual)
            verify(exactly = 1) { service.findAll().size }
        }
    }

    @Nested
    inner class WhenFindByID {

        @Test
        fun `Should repository find an Transaction by ID`() {
            val expected: Optional<TransactionImported> = Optional.of(transaction)
            every { repository.findById(any()) } returns expected
            val actual = service.findById(transaction.id)
            Assertions.assertEquals(expected, actual)
            verify(exactly = 1) { service.findById(transaction.id) }
        }
    }

    @Nested
    inner class WhenDeletingById {
        @Test
        fun `should delete Transaction`() {
            val expected: Optional<TransactionImported> = Optional.of(transaction)
            every { repository.findById(any()) } returns expected
            every { repository.deleteById(any()) } just runs
            service.deleteById(expected.get().id)
            verify(exactly = 1) { service.deleteById(any()) }
        }
    }
}
