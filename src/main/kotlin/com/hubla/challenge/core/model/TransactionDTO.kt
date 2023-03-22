package com.hubla.challenge.core.model

import java.time.LocalDate

data class TransactionDTO(
    val id: Long?,
    val type: Int,
    val product: String,
    val valueTransaction: Int,
    val seller: String,
    val createdAt: LocalDate = LocalDate.now()
)

