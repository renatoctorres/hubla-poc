package com.hubla.challenge.core.model

import java.util.*

data class ImportationDTO(
    val id: Long,
    val createdAt: Date = Date(),
    val transactions: List<TransactionDTO>
)
