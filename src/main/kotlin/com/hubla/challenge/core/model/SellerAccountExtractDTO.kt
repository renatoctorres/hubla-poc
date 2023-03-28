package com.hubla.challenge.core.model

data class SellerAccountExtractDTO(
    val account: SellerAccountDTO,
    val transactions: List<TransactionDTO>
)
