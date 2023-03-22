package com.hubla.challenge.core.model

import java.time.LocalDate

data class ImportationDTO(
    val id: Long?,
    val createdAt: LocalDate = LocalDate.now(),
    val transactions : List<TransactionDTO>
)
