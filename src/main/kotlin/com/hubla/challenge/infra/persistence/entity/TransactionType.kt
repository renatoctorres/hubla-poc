package com.hubla.challenge.infra.persistence.entity

enum class TransactionType(
    val code: Int,
    val description: String,
    val nature: String,
    val signal: String
) {
    PRODUCER_SALE(code = 1, description = "Producer Sale", nature = "Input", signal = "+"),
    AFFILIATE_SALE(code = 2, description = "Affiliate Sale", nature = "Input", signal = "+"),
    COMMISSION_PAID(code = 3, description = "Commission Payed", nature = "Output", signal = "-"),
    COMMISSION_RECEIVED(code = 4, description = "Commission Received", nature = "Input", signal = "+");

    companion object {
        private val map = TransactionType.values().associateBy { it.code }
        operator fun get(value: Int) = map[value]
    }

}
