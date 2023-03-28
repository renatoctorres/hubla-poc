package com.hubla.challenge.core.model
data class TransactionDTO(
    val id: Long,
    val type: Int,
    val product: ProductDTO,
    val valueTransaction: Int,
    val seller: SellerDTO,
    var date: String,
    val idImportation: Long
)
