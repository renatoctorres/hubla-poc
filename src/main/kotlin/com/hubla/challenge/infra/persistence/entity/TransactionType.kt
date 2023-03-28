package com.hubla.challenge.infra.persistence.entity

enum class TransactionType(
    val code: Int,
    val description: String,
    val nature: OperationNature,
) {
    PRODUCER_SALE(code = 1, description = "Producer Sale", nature = OperationNature.CREDIT),
    AFFILIATE_SALE(code = 2, description = "Affiliate Sale", nature = OperationNature.CREDIT),
    COMMISSION_PAID(code = 3, description = "Commission Payed", nature = OperationNature.DEBIT),
    COMMISSION_RECEIVED(code = 4, description = "Commission Received", nature = OperationNature.CREDIT);

    companion object {
        private val map = TransactionType.values().associateBy { it.code }
        operator fun get(value: Int) = map[value]
    }

}
