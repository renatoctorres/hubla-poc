package com.hubla.challenge.core.service

import com.hubla.challenge.core.model.ImportationDTO
import com.hubla.challenge.core.model.extensions.toEntity
import com.hubla.challenge.infra.persistence.entity.Importation
import com.hubla.challenge.infra.persistence.repository.ImportationRepository
import org.springframework.stereotype.Service
import java.util.*

@Service
class ImportationService(
    private val repository: ImportationRepository
) {
    fun save(importation: Importation): Importation = repository.saveAndFlush(importation)
    fun findAll(): List<Importation> = repository.findAll()
    fun findById(id: Long): Optional<Importation> = repository.findById(id)
    fun deleteById(id: Long) = repository.deleteById(id)
}
