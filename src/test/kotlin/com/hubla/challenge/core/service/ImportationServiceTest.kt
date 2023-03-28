package com.hubla.challenge.core.service

import com.hubla.challenge.core.model.objectmother.ImportationObjectMother
import com.hubla.challenge.infra.persistence.entity.Importation
import com.hubla.challenge.infra.persistence.repository.ImportationRepository
import io.mockk.every
import io.mockk.just
import io.mockk.mockk
import io.mockk.runs
import io.mockk.verify
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test
import java.util.*

internal class ImportationServiceTest {
    private val repository: ImportationRepository = mockk(relaxed = true)
    private val service: ImportationService = ImportationService(repository = repository)
    private val importation: Importation = ImportationObjectMother.build()

    @Nested
    inner class WhenCreatingImportation {
        @Test
        fun `it should repository persist Importation`() {
            val expected: Importation = importation
            every { repository.saveAndFlush(any()) } returns expected
            val actual = service.save(expected)
            assertEquals(expected, actual)
            verify(exactly = 1) { repository.saveAndFlush(any()) }
        }
    }

    @Nested
    inner class WhenFindAll {
        @Test
        fun `Should repository find All Importations`() {
            val expected: Importation = importation
            every { repository.findAll() } returns listOf(expected)
            val actual = service.findAll()[0]
            assertEquals(expected, actual)
            verify(exactly = 1) { service.findAll().size }
        }
    }

    @Nested
    inner class WhenFindByID {

        @Test
        fun `Should repository find an Importation by ID`() {
            val expected: Optional<Importation> = Optional.of(importation)
            every { repository.findById(any()) } returns expected
            val actual = service.findById(1L)
            assertEquals(expected, actual)
            verify(exactly = 1) { service.findById(1L) }
        }
    }

    @Nested
    inner class WhenDeletingById {
        @Test
        fun `should delete Importation`() {
            val expected: Optional<Importation> = Optional.of(importation)
            every { repository.findById(any()) } returns expected
            every { repository.deleteById(any()) } just runs
            service.deleteById(expected.get().id)
            verify(exactly = 1) { service.deleteById(any()) }
        }
    }
}
